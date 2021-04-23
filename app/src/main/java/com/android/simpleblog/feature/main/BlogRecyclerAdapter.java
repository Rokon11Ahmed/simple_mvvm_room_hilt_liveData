package com.android.simpleblog.feature.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.android.simpleblog.R;
import com.android.simpleblog.common.models.Blog;
import com.android.simpleblog.feature.blogdetails.BlogDetailsActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {
    ArrayList<Blog> blogList = new ArrayList<>();

    public BlogRecyclerAdapter(ArrayList<Blog> blogList) {
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_blog_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        holder.titleText.setText(blog.getTitle());
        Glide.with(holder.imageView).load(blog.getCoverPhoto()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), BlogDetailsActivity.class);
                intent.putExtra("id", String.valueOf(blog.getId()));
                intent.putExtra("title", blog.getTitle());
                intent.putExtra("descreption", blog.getDescription());
                intent.putExtra("cover_photo", blog.getCoverPhoto());
                intent.putStringArrayListExtra("categories", blog.getCategories());
                intent.putExtra("author_name", blog.getAuthor().getName());
                intent.putExtra("author_avater", blog.getAuthor().getAvatar());
                intent.putExtra("author_profession", blog.getAuthor().getProfession());
                holder.imageView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.blog_title_textView);
            imageView = itemView.findViewById(R.id.blog_header_image);
        }
    }
}
