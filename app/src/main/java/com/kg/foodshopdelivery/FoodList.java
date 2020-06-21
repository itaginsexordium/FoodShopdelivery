package com.kg.foodshopdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kg.foodshopdelivery.Interface.ItemClickListener;
import com.kg.foodshopdelivery.VievHolder.FoodViewHolder;
import com.kg.foodshopdelivery.model.Food;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        //firebase
        database = FirebaseDatabase.getInstance();
        foodList=database.getReference("Foods");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
       // layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //получаем интент
        if (getIntent() !=null)
            categoryId=getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId !=null)
        {
            loadListFood(categoryId);
        }

    }

    private void loadListFood(String categoryId) {
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(foodList.orderByChild("menuId").equalTo(categoryId), Food.class).build();

        adapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
           @Override
           protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
               holder.food_name.setText(model.getName());
               Picasso.get().load(model.getImage()).into(holder.food_image);

                   final Food local = model;
                   holder.setItemClickListener(new ItemClickListener() {
                       @Override
                       public void onClick(View view, int position, boolean isLongClick) {
                           Intent foodDetail =new Intent(FoodList.this,FoodDetail.class);
                           foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                           startActivity(foodDetail);
                       }
                   });
           }

           @NonNull
           @Override
           public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
               return new FoodViewHolder(itemView);
           }
       };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        Log.d("Tag",""+adapter.getItemCount());
    }
}
