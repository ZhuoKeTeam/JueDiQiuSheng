package cc.zkteam.juediqiusheng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.helper.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.juediqiusheng.R;

/**
 * Created by Doraemon on 2017/10/29.
 */

public class NewsAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private static int Type_title = 0;
    private static int Type_content = 1;

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = null;
        if (viewType == Type_content) {
            contentView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_hot_news, parent, false);
            return new ContentHolder(contentView);
        } else if (viewType == Type_title) {
            contentView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_hot_news_title, parent, false);
            return new TitleHolder(contentView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentHolder) {
            ImageLoader.loadImage(((ContentHolder) holder).itemIv, "http://imgs.gamersky.com/upimg/2017/201707271436528741.jpg");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 4 == 0 ? Type_title : Type_content;
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_title)
        SimpleDraweeView itemIv;
        @BindView(R.id.tv_title)
        TextView titleTv;

        public ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    static class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView titleTv;

        public TitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
