package com.sorinaidea.arayeshgah.layout;

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

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.datahelper.Gender;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetGiftFragment.OnGetGiftFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GetGiftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetGiftFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnGetGiftFragmentInteractionListener mListener;

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onGetGiftFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGetGiftFragmentInteractionListener) {
            mListener = (OnGetGiftFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnGetGiftFragmentInteractionListener {
        // TODO: Update argument type and name
        void onGetGiftFragmentInteraction(Uri uri);
    }

}
