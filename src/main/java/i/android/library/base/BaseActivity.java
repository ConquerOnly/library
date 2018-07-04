package i.android.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by root on 12/4/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayout());
        initVariables();
        initViews(savedInstanceState);
        loadData();
    }
    protected abstract void loadData();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initVariables();

    protected  abstract int provideLayout();




}
