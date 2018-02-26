package com.sorinaidea.arayeshgah.layout;

import android.content.Context;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.ui.SorinaActivity;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.SorinaApplication;
import com.sorinaidea.arayeshgah.util.Util;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActivationFragment.OnActivationFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnActivationFragmentInteractionListener mListener;

    public ActivationFragment() {
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
    public static ActivationFragment newInstance(String param1, String param2) {
        ActivationFragment fragment = new ActivationFragment();
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
        return inflater.inflate(R.layout.fragment_activation_code, container, false);
    }

    private Button btnNextStep;
    private TextInputEditText edtActivationCode;
    private TextView txtIconActivationCode;
    private TextView txtSendAgain;
    private TextInputLayout inputLayoutActivationCode;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        btnNextStep = (Button) view.findViewById(R.id.btnNextStep);
        txtIconActivationCode = (TextView) view.findViewById(R.id.txtIconActivationCode);
        txtSendAgain = (TextView) view.findViewById(R.id.txtSendAgain);
        edtActivationCode = (TextInputEditText) view.findViewById(R.id.edtActivationCode);
        inputLayoutActivationCode = (TextInputLayout) view.findViewById(R.id.inputLayoutActivationCode);


        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "sorina://start";
//                mListener.onFragmentInteraction(Uri.parse(url));
            }
        });

        txtSendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        Typeface iconFont = FontManager.getTypeface(getActivity().getApplicationContext(), FontManager.MATERIAL_ICONS);
        FontManager.markAsIconContainer(txtIconActivationCode, iconFont);
        FontManager.markAsIconContainer(btnNextStep, iconFont);

        edtActivationCode.addTextChangedListener(new MyTextWatcher(edtActivationCode));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onActivationFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActivationFragmentInteractionListener) {
            mListener = (OnActivationFragmentInteractionListener) context;
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
    public interface OnActivationFragmentInteractionListener {
        // TODO: Update argument type and name
        void onActivationFragmentInteraction(Uri uri);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edtActivationCode:
                    if (edtActivationCode.getText().length()>3){
                        validatePhoneNumber();

                    }
                    break;
            }
        }
    }

    private boolean validatePhoneNumber() {
//        if (edtActivationCode.getText().toString().trim().isEmpty()) {
//            inputLayoutActivationCode.setError(getString(R.string.error_empty_phone));
//            edtActivationCode.requestFocus();
//            return false;
//        } else if (!Pattern.matches(Util.CONSTANTS.REGEX_PHONE, edtActivationCode.getText().toString())) {
//            inputLayoutActivationCode.setError(getString(R.string.error_invalid_phone));
//            edtActivationCode.requestFocus();
//            return false;
//        } else {
//            inputLayoutActivationCode.setErrorEnabled(false);
//        }
        return true;
    }
}
