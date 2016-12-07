package com.example.administrator.myapplication.fragment;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public class SimpleTextCellFragment extends Fragment {

	TextView label;
	EditText edit;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_inputcell_simpletext,
				container);
		label=(TextView) view.findViewById(R.id.tv_label);
		edit=(EditText) view.findViewById(R.id.et_edit);
		return view;

	}
	public void setLabelText(String text){
		label.setText(text);
	}

	public void setHintText(String text){
		edit.setHint(text);
	}

	public void setPassword(boolean isPassword){
		if (isPassword) {
			edit.setInputType(EditorInfo.TYPE_CLASS_TEXT
					| EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD);

		} else {
			edit.setInputType(EditorInfo.TYPE_CLASS_TEXT);
		}
	}
}
