package com.taboola.taboolasample;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taboola.android.TaboolaWidget;
import com.taboola.android.utils.SdkDetailsHelper;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<FeedListItem> mData;

    public FeedAdapter() {
        List<FeedListItem> randomImages = new ArrayList<>();
        randomImages.add(new RandomItem("https://images.unsplash.com/photo-1426170042593-200f250dfdaf?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9&s=c1627023e5cc6260cf97930623becf89"));
        randomImages.add(new RandomItem("https://images.unsplash.com/photo-1519255680055-56f0a6b27366?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9&s=8a1648322dd34c05f80ad3034b38be5d"));
        randomImages.add(new RandomItem("https://images.unsplash.com/photo-1522150461590-90b858186658?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9&s=a0a0f55a60b16d28d192da080ca29d0b"));
        randomImages.add(new RandomItem("https://images.unsplash.com/photo-1521851935320-f0583e21bcff?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9&s=095cb4b5c5553cba16f4924240f05414"));
        randomImages.add(new RandomItem("https://images.unsplash.com/photo-1462926795244-b273f8a5454f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9&s=8cbc77dfb4814087f45a8db614d335fd"));
        randomImages.add(new RandomItem("https://images.unsplash.com/photo-1463717738788-85fcacb6ac3d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9&s=def20d19c740de15f45ce13ef66fbee7"));
        randomImages.add(new RandomItem("https://images.unsplash.com/photo-1526781000967-49cfae83f5ac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9&s=0233a3b85f66d4610fc21a32d8286898"));
        randomImages.add(new RandomItem("https://images.unsplash.com/photo-1528399258088-04d894b1dbd6?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjF9&s=6de5fdd2170b59afc1f31f41a4f4810f"));
        Collections.shuffle(randomImages);
        randomImages.add(new TaboolaView());
        mData = randomImages;
    }

    public static class RandomImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        RandomImageViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.feed_item_iv);
            textView = view.findViewById(R.id.feed_item_tv);
        }
    }

    public static class ViewHolderTaboola extends RecyclerView.ViewHolder{
        private TaboolaWidget mTaboolaWidget;

        ViewHolderTaboola(View v) {
            super(v);
            mTaboolaWidget = v.findViewById(R.id.taboola_widget);
            mTaboolaWidget
                    .setPageType("article")
                    .setPageUrl("http://www.example.com")
                    .setMode("thumbnails-feed")
                    .setPlacement("feed-sample-app")
                    .setTargetType("mix")
                    .setPublisher("betterbytheminute-app")
                    .setInterceptScroll(true);

            mTaboolaWidget.fetchContent();
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){

            case FeedListItem.ItemType.TABOOLA_ITEM:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.taboola_row_item, parent, false);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
                params.height = SdkDetailsHelper.getDisplayHeight(v.getContext());
                v.setLayoutParams(params);
                return new ViewHolderTaboola(v);

            default:
            case FeedListItem.ItemType.RANDOM_ITEM:
                View appCompatImageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_item, parent, false);
                return new RandomImageViewHolder(appCompatImageView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FeedListItem item = getItem(position);

        if(item.type == FeedListItem.ItemType.RANDOM_ITEM){
            RandomImageViewHolder vh = (RandomImageViewHolder)holder;
            RandomItem randomItem = (RandomItem) item;
            final ImageView imageView = vh.imageView;
            Picasso.with(imageView.getContext()).load(randomItem.url).into(imageView);
            vh.textView.setText(randomItem.randomText);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    private FeedListItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        FeedListItem item = getItem(position);
        return item.type;
    }

    abstract static class FeedListItem {

        @Retention(SOURCE)
        @IntDef({ItemType.TABOOLA_ITEM, ItemType.RANDOM_ITEM,})

        public @interface ItemType {
            int TABOOLA_ITEM = 1;
            int RANDOM_ITEM = 2;
        }

        @ItemType
        int type;

        FeedListItem(@ItemType int type) {
            this.type = type;
        }
    }

    static class TaboolaView extends FeedListItem {

        TaboolaView() {
            super(ItemType.TABOOLA_ITEM);
        }
    }

    static class RandomItem extends FeedListItem {

        private final String url;
        private final String randomText;

        RandomItem(String imageUrl) {
            super(ItemType.RANDOM_ITEM);
            url = imageUrl;
            randomText = getRandomText();
        }

        private String getRandomText() {
            Random random = new Random();
            int newValue = random.nextInt();

            switch (newValue % 10){

                default:
                case 1:
                case 9:
                    return "Jack Tar me keel lanyard grog doubloon gabion furl Yellow Jack flogging. Booty galleon cable Sail ho lugsail yard draught driver run a rig draft. Wench nipperkin mutiny bowsprit lad Arr draft overhaul salmagundi bilged on her anchor.";

                case 2:
                case 8:
                    return "SFire in the hole grapple plunder matey swing the lead clipper hang the jib skysail handsomely Blimey. Sink me poop deck pressgang hands Yellow Jack lugger haul wind run a rig Sea Legs walk the plank. Boatswain fluke pinnace salmagundi nipper plunder landlubber or just lubber gibbet barque topgallant";

                case 3:
                case 7:
                    return "Transom bilged on her anchor deadlights Chain Shot topgallant doubloon lugger code of conduct man-of-war spanker. Cackle fruit ahoy fore walk the plank main sheet crimp ye nipper coffer Sea Legs. Bowsprit gangplank long boat gangway strike colors parley gibbet jib take a caulk fathom.";

                case 4:
                case 6:
                    return "Shrimps paste has to have a sun-dried, puréed carrots component. Herring tastes best with fish sauce and lots of cinnamon. Flatten a dozen oysters, tuna, and cumin in a large bucket over medium heat, warm for a handfull minutes and whisk with some squid.";

                case 5:
                    return "Stern capstan chantey sloop Sea Legs ye Jack Tar rigging sheet booty. Jack Tar jib hogshead grog belay cable keelhaul sloop Shiver me timbers jolly boat. Grog blossom lanyard handsomely chandler run a rig dance the hempen jig Spanish Main landlubber or just lubber measured fer yer chains topsail.";

            }
        }
    }

}
