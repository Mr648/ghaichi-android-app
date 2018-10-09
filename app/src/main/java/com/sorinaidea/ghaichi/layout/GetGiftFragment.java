package com.sorinaidea.ghaichi.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.datahelper.Gender;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.util.Util;

public class GetGiftFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GetGiftFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetGiftFragment newInstance(String param1, String param2) {
        GetGiftFragment fragment = new GetGiftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tell_friends, container, false);
    }

    private TextView txtGetGiftMsg;
    private TextView txtGiftCode;
    private TextView txtIconShare;

    private Gender selectedGender;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        txtGetGiftMsg = (TextView) view.findViewById(R.id.txtGetGiftMsg);
        txtGiftCode = (TextView) view.findViewById(R.id.txtGiftCode);
        txtIconShare = (TextView) view.findViewById(R.id.txtIconShare);

        Typeface iconFont = FontManager.getTypeface(getActivity().getApplicationContext(), FontManager.MATERIAL_ICONS);
        Typeface irsansFont = FontManager.getTypeface(getActivity().getApplicationContext(), FontManager.IRANSANS_TEXTS);

        FontManager.setFont(txtIconShare, iconFont);
        FontManager.setFont(txtGetGiftMsg, irsansFont);

        txtIconShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                String message = Util.CONSTANTS.SHARE_GIFT_CODE_MESSAGE + txtGiftCode.getText();
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getString(R.string.share)));
            }
        });

    }

    // TODO: Rename method, add argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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
