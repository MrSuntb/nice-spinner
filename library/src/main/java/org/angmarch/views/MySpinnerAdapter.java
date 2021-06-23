package org.angmarch.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*
 * Copyright (C) 2015 Angelo Marchesin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class MySpinnerAdapter<T> extends NiceSpinnerBaseAdapter {

    private final List<T> items;

    MySpinnerAdapter(
            Context context,
            List<T> items,
            int textColor,
            int backgroundSelector,
            SpinnerTextFormatter spinnerTextFormatter,
            PopUpTextAlignment horizontalAlignment
    ) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter, horizontalAlignment);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= selectedIndex) {
            return items.get(position + 1);
        } else {
            return items.get(position);
        }
    }

    @Override
    public T getItemInDataset(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        TextView tvSpinnerText;
        ImageView ivChecked;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.spinner_list_item2, null);
            tvSpinnerText = convertView.findViewById(R.id.tv_spinner_text);
            ivChecked = convertView.findViewById(R.id.iv_checked);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                convertView.setBackground(ContextCompat.getDrawable(context, backgroundSelector));
            }
            convertView.setTag(new SpinnerItemViewHolder(tvSpinnerText,ivChecked));
        } else {
            tvSpinnerText = ((SpinnerItemViewHolder) convertView.getTag()).tvSpinnerText;
            ivChecked = ((SpinnerItemViewHolder) convertView.getTag()).ivChecked;
        }

        if (position == selectedIndex ) {
            tvSpinnerText.setTextColor(context.getResources().getColor(R.color.colorTextSelect));
            ivChecked.setVisibility(View.VISIBLE);
        } else {
            tvSpinnerText.setTextColor(context.getResources().getColor(R.color.colorTextWeak));
            ivChecked.setVisibility(View.INVISIBLE);
        }

        tvSpinnerText.setText(items.get(position).toString());
        setTextHorizontalAlignment(tvSpinnerText);

        return convertView;
    }

    static class SpinnerItemViewHolder {
        TextView tvSpinnerText;
        ImageView ivChecked;
        SpinnerItemViewHolder(TextView textView, ImageView imageView) {
            this.tvSpinnerText = textView;
            this.ivChecked = imageView;
        }
    }
}