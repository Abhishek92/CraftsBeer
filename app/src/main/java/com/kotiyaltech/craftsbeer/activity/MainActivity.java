package com.kotiyaltech.craftsbeer.activity;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kotiyaltech.craftsbeer.R;
import com.kotiyaltech.craftsbeer.adapter.BeerListAdapter;
import com.kotiyaltech.craftsbeer.databinding.ActivityMainBinding;
import com.kotiyaltech.craftsbeer.dialog.DialogUtil;
import com.kotiyaltech.craftsbeer.model.BeerItem;
import com.kotiyaltech.craftsbeer.util.Util;
import com.kotiyaltech.craftsbeer.viewmodel.BeerCraftViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BeerCraftViewModel beerCraftViewModel;
    private boolean isSortedOrder = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpRecyclerView();
        loadBeerItemList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.query_hint));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                String searchTerm = Util.createLikeQuery(newText);
                beerCraftViewModel.searchBeerItems(searchTerm);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                String searchTerm = Util.createLikeQuery(query);
                beerCraftViewModel.searchBeerItems(searchTerm);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_sort){
            sortBeerList();
        }
        else if(item.getItemId() == R.id.action_filter){
            filterBeerList();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.beerListRv.addItemDecoration(dividerItemDecoration);
        binding.beerListRv.setLayoutManager(linearLayoutManager);
    }

    private void loadBeerItemList() {
        beerCraftViewModel = ViewModelProviders.of(this).get(BeerCraftViewModel.class);
        beerCraftViewModel.getBeerList().observe(this, new Observer<List<BeerItem>>() {
            @Override
            public void onChanged(@Nullable List<BeerItem> beerItems) {
                binding.progressBar.setVisibility(View.GONE);
                if(Util.listNotNull(beerItems)) {
                    BeerListAdapter beerListAdapter = new BeerListAdapter(beerItems);
                    binding.beerListRv.setAdapter(beerListAdapter);
                }
            }
        });
    }


    private void sortBeerList() {
        if(isSortedOrder) {
            isSortedOrder = false;
            beerCraftViewModel.loadBearListInDescendingOrder();
            Toast.makeText(this, R.string.sort_desc_order, Toast.LENGTH_LONG).show();
        }
        else{
            isSortedOrder = true;
            beerCraftViewModel.loadBeerItem();
            Toast.makeText(this, R.string.sort_asc_order, Toast.LENGTH_LONG).show();
        }
    }

    private void filterBeerList() {
        DialogUtil.showFilterDialog(this, new DialogUtil.FilterEventListener() {
            @Override
            public void onBeerStyleSelected(String style) {
                if(!TextUtils.isEmpty(style)){
                    style = style.equals(getString(R.string.all)) ? "" : style;
                    beerCraftViewModel.filterBeerItems(Util.createLikeQuery(style));
                }
            }
        });
    }


}
