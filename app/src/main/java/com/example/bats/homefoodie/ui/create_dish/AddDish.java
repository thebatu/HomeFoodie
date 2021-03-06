package com.example.bats.homefoodie.ui.create_dish;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.bats.homefoodie.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDish extends AppCompatActivity {

    @BindView(R.id.parent_linear_layout2)
    LinearLayout parentLayout;
    @BindView(R.id.activity_add_dish)
    ScrollView scrollView;
    public static final int PICK_IMAGE = 1;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        ButterKnife.bind(this);
        context = this;
    }

    /**
     * remove add_ingredient_row from the stack of the containing layout
     * @param v view of the current activity
     */
    public void onDelete(View v) {
        if (parentLayout.getChildCount() > 0) {
            parentLayout.removeView((View) v.getParent());
        }
    }

    /**
     * method inflates a row of editText and an image wrapped in Constraints Layout.
     * and adds it before the add dish button
     * @param view the current activities view.
     */
    public void onAddIngredient(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.row_add_ingredient, null);
        // Add the new row before the add dish button.
        parentLayout.addView(rowView, parentLayout.getChildCount() - 1);
        focusOnView();
    }

    /**
     * scroll smoothly to the button of the button add ingredient{@add_field_button}
     */
    private void focusOnView() {
        new Handler().post(() -> ObjectAnimator.ofInt(scrollView, "scrollY",
                scrollView.getBottom()).setDuration(700).start());
    }

    /**
     * select a dish image intent
     */
    private void dishImageProfile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    /**
     *
     * @param requestCode identifier
     * @param resultCode result code
     * @param data image
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }else {
                try {
                    //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
                    InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }








}
