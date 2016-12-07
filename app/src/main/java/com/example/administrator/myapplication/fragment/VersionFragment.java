package com.example.administrator.myapplication.fragment;


import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public class VersionFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_version, null);
		TextView version = (TextView) view.findViewById(R.id.tv_appnameversion);

		PackageManager pkgm = getActivity().getPackageManager();
		try {
			PackageInfo packageInfo = pkgm.getPackageInfo(getActivity()
					.getPackageName(), 0);

			version.setText(packageInfo.packageName + " "
					+ packageInfo.versionName);
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			version.setText("ERROR");
		}

		return view;
	}

}
