package i.android.library.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by root on 2/14/18.
 */

public abstract class BaseAdapter<T> {
    private final ArrayList<T> tList;
    private final BaseActivity context;

    public BaseAdapter(ArrayList<T> tList, BaseActivity context) {
        this.tList = tList;
        this.context = context;
    }

    public int getCount(){
        return tList.size();
    }
    public T getItem(final int position){
        return tList.get(position);
    }
    public long getItemId(final int position){
        return position;
    }
    public View getView(final int position, View convertView, final ViewGroup viewGroup) {
        final Holder holder;
        if (convertView == null) {
            holder = onCreateViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
            onBindViewHolder(holder);
        }
        return convertView;
    }

    abstract Holder onCreateViewHolder();
    abstract void onBindViewHolder(Holder holder);

    class Holder{
        public final View itemView;
        public Holder(View itemView) {
            this.itemView = itemView;
        }
    }
}
