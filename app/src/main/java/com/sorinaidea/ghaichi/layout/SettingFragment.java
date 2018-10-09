package com.sorinaidea.ghaichi.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.adapter.FAQAdabper;
import com.sorinaidea.ghaichi.model.FAQ;
import com.sorinaidea.ghaichi.util.FontManager;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }


    Typeface iranSansFont;
    Typeface mtdFont;

    private TextView txtNotification;
    private TextView txtTheme;
    private TextView txtLanguage;

    private Button btnNotification;
    private Button btnTheme;
    private Button btnLanguage;
    private Button btnLogout;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mtdFont = FontManager.getTypeface(getContext(), FontManager.MATERIAL_ICONS);
        iranSansFont = FontManager.getTypeface(getContext(), FontManager.IRANSANS_TEXTS);


        txtNotification = (TextView) view.findViewById(R.id.txtNotification);
        txtTheme = (TextView) view.findViewById(R.id.txtTheme);
        txtLanguage = (TextView) view.findViewById(R.id.txtLanguage);
        btnNotification = (Button) view.findViewById(R.id.btnNotification);
        btnTheme = (Button) view.findViewById(R.id.btnTheme);
        btnLanguage = (Button) view.findViewById(R.id.btnLanguage);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        setFonts();

    }


    private void setFonts() {

        FontManager.setFont(txtNotification, iranSansFont);
        FontManager.setFont(txtTheme, iranSansFont);
        FontManager.setFont(txtLanguage, iranSansFont);
        FontManager.setFont(btnLogout, iranSansFont);

        FontManager.setFont(btnNotification, mtdFont);
        FontManager.setFont(btnTheme, mtdFont);
        FontManager.setFont(btnLanguage, mtdFont);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
