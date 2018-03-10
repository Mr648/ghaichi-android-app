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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.datahelper.Gender;
import com.sorinaidea.arayeshgah.util.FontManager;
import com.sorinaidea.arayeshgah.util.SorinaApplication;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalInfoFragment.OnPersonalInfoFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnPersonalInfoFragmentInteractionListener mListener;

    public PersonalInfoFragment() {
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
    public static PersonalInfoFragment newInstance(String param1, String param2) {
        PersonalInfoFragment fragment = new PersonalInfoFragment();
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
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    private Button btnNextStep;
    private Spinner spnGender;
    private TextInputEditText edtName;
    private TextInputEditText edtFamily;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutFamily;

    private Gender selectedGender;
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        btnNextStep = (Button) view.findViewById(R.id.btnNextStep);
        spnGender = (Spinner) view.findViewById(R.id.spnGender);


        edtName = (TextInputEditText) view.findViewById(R.id.edtName);
        edtFamily = (TextInputEditText) view.findViewById(R.id.edtFamily);
        inputLayoutName = (TextInputLayout) view.findViewById(R.id.inputLayoutName);
        inputLayoutName = (TextInputLayout) view.findViewById(R.id.inputLayoutFamily);


        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "sorina://start";
//                mListener.onFragmentInteraction(Uri.parse(url));
            }
        });

//        final ArrayList<String> spnGenderItems = new ArrayList();
//
//        spnGenderItems.add("آقا");
//        spnGenderItems.add("خانم");
//
//        ArrayAdapter<String> spnGenderAdapter =
//                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spnGenderItems);
//
//        spnGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spnGender.setAdapter(spnGenderAdapter);

        spnGender.setSelection(0);

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = Gender.values()[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGender = Gender.values()[0];
            }
        });

        Typeface iconFont = FontManager.getTypeface(getActivity().getApplicationContext(), FontManager.MATERIAL_ICONS);
        FontManager.setFont(btnNextStep, iconFont);

        edtName.addTextChangedListener(new MyTextWatcher(edtName));
        edtFamily.addTextChangedListener(new MyTextWatcher(edtFamily));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPersonalInfoFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPersonalInfoFragmentInteractionListener) {
            mListener = (OnPersonalInfoFragmentInteractionListener) context;
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
    public interface OnPersonalInfoFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPersonalInfoFragmentInteraction(Uri uri);
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
                case R.id.edtFamily:
                    if (edtFamily.getText().length() > 3) {
                        validateName();
                    }
                    break;
                case R.id.edtName:
                    if (edtName.getText().length() > 3) {
                        validateFamily();
                    }
                    break;
            }
        }
    }

    private boolean validateName() {
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


    private boolean validateFamily() {
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
