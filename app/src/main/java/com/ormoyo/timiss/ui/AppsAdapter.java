package com.ormoyo.timiss.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.ormoyo.timiss.AppInfo;
import com.ormoyo.timiss.AppProfile;
import com.ormoyo.timiss.Timiss;
import com.ormoyo.timiss.R;
import com.ormoyo.timiss.ui.fragments.CreateProfileFragment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class AppsAdapter extends ArrayAdapter<AppInfo> {
    private final AppInfo[] values;

    public AppsAdapter(Context context, AppInfo[] values) {
        super(context, -1, values);
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View rowView = inflater.inflate(R.layout.app_layout, parent, false);
//        ImageView imageView = rowView.findViewById(R.id.app_icon);
//        TextView nameView = rowView.findViewById(R.id.app_name);
//        TextView hoursView = rowView.findViewById(R.id.app_hours);
//        TextView minutesView = rowView.findViewById(R.id.app_minutes);
//        TextView secondsView = rowView.findViewById(R.id.app_seconds);
//        SwitchMaterial switchView = rowView.findViewById(R.id.profile_enabled);
//
//        Prototype application = Prototype.getInstance();
//        Account currentAccount = application.getCurrentAccount();
//
//        AppInfo info = values[position];
//        nameView.setText(info.getName());
//        AppProfile profile = null;
//        for(AppProfile p : currentAccount.getProfiles()) {
//            if(p.getPackageName().equals(info.getPackageName())) {
//                profile = p;
//                break;
//            }
//        }
//
//        boolean doesHaveProfile = profile != null;
//        if(doesHaveProfile) {
//            int hours = profile.getTime() / 3600;
//            int minutes = profile.getTime() % 3600 / 60;
//            int seconds = profile.getTime() % 3600 % 60;
//            if(hours > 0)
//                hoursView.setText(hours + " " + getContext().getString(R.string.hours));
//            else {
//                ((ConstraintLayout.LayoutParams) minutesView.getLayoutParams()).leftMargin = 0;
//                minutesView.requestLayout();
//            }
//            if(minutes > 0)
//                minutesView.setText(minutes + " " + getContext().getString(R.string.minutes));
//            else {
//                ((ConstraintLayout.LayoutParams) secondsView.getLayoutParams()).leftMargin = 0;
//                secondsView.requestLayout();
//            }
//            if(seconds > 0)
//                secondsView.setText(seconds + " " + getContext().getString(R.string.seconds));
//            hoursView.setTextColor(hoursView.getTextColors().withAlpha(75));
//            minutesView.setTextColor(minutesView.getTextColors().withAlpha(75));
//            secondsView.setTextColor(secondsView.getTextColors().withAlpha(75));
//        }
//
//        FragmentActivity activity = (FragmentActivity) getContext();
//        CreateProfileFragment profileFragment = (CreateProfileFragment) activity.getSupportFragmentManager().findFragmentByTag("create_profile");
//
//        switchView.setChecked(doesHaveProfile || profileFragment != null && profileFragment.getApp().equals(info));
//        switchView.jumpDrawablesToCurrentState();
//
//        AppProfile p = profile;
//        switchView.setOnCheckedChangeListener((compoundButton, b) -> {
//            if(b) {
//                CreateProfileFragment fragment = new CreateProfileFragment(info);
//                fragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(), "create_profile");
//                fragment.setOnCancelListener((dialogInterface) -> {
//                    switchView.setChecked(false);
//                });
//            }else {
//                hoursView.setText("");
//                minutesView.setText("");
//                secondsView.setText("");
//
//                SharedPreferences profilePreferences = getContext().getSharedPreferences("ACCOUNT_PROFILES", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = profilePreferences.edit();
//
//
//                Set<String> apps = new HashSet<>(profilePreferences.getStringSet(currentAccount.getName(), new HashSet<>()));
//                for(Iterator<String> iterator = apps.iterator(); iterator.hasNext();) {
//                    String s = iterator.next();
//                    if(s.startsWith(info.getPackageName())) {
//                        iterator.remove();
//                    }
//                }
//
//                editor.putStringSet(currentAccount.getName(), apps);
//                editor.apply();
//            }
//        });
//        imageView.setImageDrawable(info.getDrawable());
//        scaleImage(imageView, 75, 75);
        return null;
    }

    private void scaleImage(ImageView view, int width, int height)  {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = null;

        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            throw new NoSuchElementException("No drawable on given view");
        }

        // Get current dimensions AND the desired bounding box
        int w = 0;

        try {
            w = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int h = bitmap.getHeight();
        int boundingX = dpToPx(width);
        int boundingY = dpToPx(height);

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) boundingX) / w;
        float yScale = ((float) boundingY) / h;
        float scale = Math.min(xScale, yScale);

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);

        // Apply the scaled bitmap
        view.setImageDrawable(result);


        // Now change ImageView's dimensions to match the scaled image
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = width;
    }

    private int dpToPx(int dp) {
        float density = getContext().getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}
