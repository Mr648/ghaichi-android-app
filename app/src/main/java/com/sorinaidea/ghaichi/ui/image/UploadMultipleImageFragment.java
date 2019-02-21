package com.sorinaidea.ghaichi.ui.image;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.util.FontManager;


public class UploadMultipleImageFragment extends Fragment {


    private Typeface fontIranSans;
    private RecyclerView recImages;
    private FloatingActionButton fabAddImage;

    private void setupViews(View view) {
        recImages = view.findViewById(R.id.recImages);
        fabAddImage = view.findViewById(R.id.fabAddImage);
    }

    private void setFonts() {
        fontIranSans = FontManager.getTypeface(getContext(), FontManager.IRANSANS_TEXTS);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload_multiple_image, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        setupViews(view);
        setFonts();

    }
}
