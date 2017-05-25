package activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.noctisdrakon.librarytest.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ItemsAdapter;
import interfaces.OnRefreshEventListener;
import model.Item;
import view.RecyclerViewLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshEventListener {

    private static final String TAG = "MainActivity";
    RecyclerViewLoader rvLoader;
    List<Item> dataSet = new ArrayList<>();
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvLoader = (RecyclerViewLoader) findViewById(R.id.rv_loader);

        rvLoader.initialize(this)
                .setRefreshStructure(null)
                .withEndlessScroll(2);


        dataSet.addAll(createDummies());
        adapter = new ItemsAdapter(dataSet, getApplicationContext(), this, "holi");
        rvLoader.setRVAdapter(adapter);

    }

    private List<Item> createDummies() {
        List<Item> list = new ArrayList<>();
        list.add(new Item());
        list.add(new Item("Viaje en Kayak", "450", "https://www.seatrek.com/wp-content/uploads/2016/10/Traditional-Sea-Kayak-3.jpg", 4));
        list.add(new Item("Alpinismo extremo", "900", "https://thenypost.files.wordpress.com/2014/06/shutterstock_145698575.jpg", 4));
        list.add(new Item("Ciclismo en la montaña", "750", "http://images.singletracks.com/blog/wp-content/uploads/2015/02/IMG_1743.jpg", 4));
        list.add(new Item("Paracaidismo", "1200", "https://i0.wp.com/www.dondeir.com/wp-content/uploads/2016/09/donde-hacer-paracaidismo-f.jpg", 4));
        list.add(new Item("Esquí acuático", "1200", "http://wwwdubai2020.com/images/684566Water-Skiing(1).jpg", 4));
        list.add(new Item("Viaje en Kayak", "450", "https://www.seatrek.com/wp-content/uploads/2016/10/Traditional-Sea-Kayak-3.jpg", 4));
        list.add(new Item("Alpinismo extremo", "900", "https://thenypost.files.wordpress.com/2014/06/shutterstock_145698575.jpg", 4));
        list.add(new Item("Ciclismo en la montaña", "750", "http://images.singletracks.com/blog/wp-content/uploads/2015/02/IMG_1743.jpg", 4));
        list.add(new Item("Paracaidismo", "1200", "https://i0.wp.com/www.dondeir.com/wp-content/uploads/2016/09/donde-hacer-paracaidismo-f.jpg", 4));
        list.add(new Item("Esquí acuático", "1200", "http://wwwdubai2020.com/images/684566Water-Skiing(1).jpg", 4));
        return list;
    }

    @Override
    public void onClick(View view) {
        Item a = (Item) view.getTag();
        Log.d(TAG, "onClick: item clicked: " + a.title);
    }

    @Override
    public void onRefreshEvent(int type) {
        Log.d(TAG, "onRefreshEvent: REFRESH EVENT!!! " + (type == REFRESH ? "REFRESH" : "RELOAD"));
        switch (type) {
            case REFRESH:
                dataSet.addAll(createDummies());

                rvLoader.post(new Runnable() {
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

                break;

            case RELOAD:
                dataSet.clear();
                dataSet.addAll(createDummies());
                adapter.notifyDataSetChanged();
                rvLoader.setRefeshLayoutLoading(false);
                break;
        }
    }
}
