package com.sorinaidea.arayeshgah.layout;

import android.content.Context;
import android.graphics.Typeface;
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

import com.sorinaidea.arayeshgah.R;
import com.sorinaidea.arayeshgah.util.FontManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarberShopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarberShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarberShopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BarberShopFragment() {
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
    public static BarberShopFragment newInstance(String param1, String param2) {
        BarberShopFragment fragment = new BarberShopFragment();
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
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    private Button btnSendCode;
    private TextInputEditText edtPhoneNumber;
    private TextView txtIconCall;
    private TextInputLayout inputLayoutPhoneNumber;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        btnSendCode = (Button) view.findViewById(R.id.btnSendCode);
        txtIconCall = (TextView) view.findViewById(R.id.txtIconCall);
        edtPhoneNumber = (TextInputEditText) view.findViewById(R.id.edtPhoneNumber);
        inputLayoutPhoneNumber = (TextInputLayout) view.findViewById(R.id.inputLayoutPhoneNumber);


        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed();
            }
        });


        Typeface iconFont = FontManager.getTypeface(getActivity().getApplicationContext(), FontManager.MATERIAL_ICONS);
        FontManager.setFont(txtIconCall, iconFont);
        edtPhoneNumber.addTextChangedListener(new MyTextWatcher(edtPhoneNumber));
    }

    // TODO: Rename method, add argument and hook method into UI event
    public void onButtonPressed( ) {
        if (mListener != null) {
            mListener.onFragmentInteraction( );
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
 *   RecyclerView recTopBarberShops;
 TextView txtMoreText;
 TextView txtMoreIcon;
 TextView txtCategory;
 LinearLayout lnrMore;

 RecyclerView recTopBarberShops2;
 TextView txtMoreText2;
 TextView txtMoreIcon2;
 TextView txtCategory2;
 LinearLayout lnrMore2;

 RecyclerView recTopBarberShops3;
 TextView txtMoreText3;
 TextView txtMoreIcon3;
 TextView txtCategory3;
 LinearLayout lnrMore3;


 private ArrayList<BarberShop> initDatasetTops() {
 ArrayList<BarberShop> mDataset = new ArrayList<>();
 Date date = new Date();
 for (int i = 0; i < 20; i++) {
 mDataset.add(new BarberShop(R.drawable.img, "آرایشگاه تست " + i));
 }
 return mDataset;
 }


 private ArrayList<Transaction> initDatasetNormals() {
 ArrayList<Transaction> mDataset = new ArrayList<>();
 Date date = new Date();
 for (int i = 0; i < 20; i++) {
 mDataset.add(new Transaction(((i % 4 == 0) ? (-1 * 100 * i) : (i * 1000)), "آرایشگاه تست " + i, "دوشنبه 30 بهمن 96"));
 }
 return mDataset;
 }

 private Typeface fontMaterialIcon;
 private Typeface fontIransans;

 @Override
 public void onCreate(@Nullable Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.fragment_barbershop);

 fontMaterialIcon = FontManager.getTypeface(getApplicationContext(), FontManager.MATERIAL_ICONS);
 fontIransans = FontManager.getTypeface(getApplicationContext(), FontManager.IRANSANS_TEXTS);

 recTopBarberShops = (RecyclerView) findViewById(R.id.recTopBarberShops);
 txtMoreText = (TextView) findViewById(R.id.txtMoreText);
 txtMoreIcon = (TextView) findViewById(R.id.txtMoreIcon);
 txtCategory = (TextView) findViewById(R.id.txtCategory);
 lnrMore = (LinearLayout) findViewById(R.id.lnrMore);

 recTopBarberShops2 = (RecyclerView) findViewById(R.id.recTopBarberShops2);
 txtMoreText2 = (TextView) findViewById(R.id.txtMoreText2);
 txtMoreIcon2 = (TextView) findViewById(R.id.txtMoreIcon2);
 txtCategory2 = (TextView) findViewById(R.id.txtCategory2);
 lnrMore2 = (LinearLayout) findViewById(R.id.lnrMore2);

 recTopBarberShops3 = (RecyclerView) findViewById(R.id.recTopBarberShops3);
 txtMoreText3 = (TextView) findViewById(R.id.txtMoreText3);
 txtMoreIcon3 = (TextView) findViewById(R.id.txtMoreIcon3);
 txtCategory3 = (TextView) findViewById(R.id.txtCategory3);
 lnrMore3 = (LinearLayout) findViewById(R.id.lnrMore3);

 recTopBarberShops.setNestedScrollingEnabled(false);

 recTopBarberShops.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
 recTopBarberShops.setAdapter(new TopBarberShopAdabper(initDatasetTops(), getApplicationContext()));

 FontManager.setFont(txtMoreIcon, fontMaterialIcon);
 FontManager.setFont(txtMoreText, fontIransans);
 FontManager.setFont(txtCategory, fontIransans);


 recTopBarberShops2.setNestedScrollingEnabled(false);

 recTopBarberShops2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
 recTopBarberShops2.setAdapter(new TopBarberShopAdabper(initDatasetTops(), getApplicationContext()));

 FontManager.setFont(txtMoreIcon2, fontMaterialIcon);
 FontManager.setFont(txtMoreText2, fontIransans);
 FontManager.setFont(txtCategory2, fontIransans);


 recTopBarberShops3.setNestedScrollingEnabled(false);
 recTopBarberShops3.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
 recTopBarberShops3.setAdapter(new TopBarberShopAdabper(initDatasetTops(), getApplicationContext()));

 FontManager.setFont(txtMoreIcon3, fontMaterialIcon);
 FontManager.setFont(txtMoreText3, fontIransans);
 FontManager.setFont(txtCategory3, fontIransans);


 txtCategory.setText("جدیدترین‌ها");
 txtCategory2.setText("پیشنهاد ویژه");
 txtCategory3.setText("خدمات جدید");

 View.OnClickListener listener = new View.OnClickListener() {
 @Override
 public void onClick(View view) {
 }
 };


 lnrMore.setOnClickListener(listener);
 lnrMore2.setOnClickListener(listener);
 lnrMore3.setOnClickListener(listener);


 //        slider = findViewById(R.id.banner_slider1);
 //        slider.setAdapter(new MainActivitySliderAdapter());
 }
 * */
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
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
                case R.id.edtPhoneNumber:
                    if (edtPhoneNumber.getText().length()>3){
                        validatePhoneNumber();

                    }
                    break;
            }
        }
    }

    private boolean validatePhoneNumber() {
//        if (edtPhoneNumber.getText().toString().trim().isEmpty()) {
//            inputLayoutPhoneNumber.setError(getString(R.string.error_empty_phone));
//            edtPhoneNumber.requestFocus();
//            return false;
//        } else if (!Pattern.matches(Util.CONSTANTS.REGEX_PHONE, edtPhoneNumber.getText().toString())) {
//            inputLayoutPhoneNumber.setError(getString(R.string.error_invalid_phone));
//            edtPhoneNumber.requestFocus();
//            return false;
//        } else {
//            inputLayoutPhoneNumber.setErrorEnabled(false);
//        }
        return true;
    }
}
