package com.example.toney.trial51;

import android.view.View;

/**
 * Created by Toney Mathews on 10/25/2016.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}