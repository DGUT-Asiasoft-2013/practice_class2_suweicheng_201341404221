package com.example.administrator.myapplication.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.io.IOException;


public class PictureInputCellFragment extends BaseInputCellFragment {

    final int REQURSTCODE_CAMERA = 1;
    final int REQURSTCODE_ALBUM = 2;
    ImageView choose;
   // TextView labelText;
    TextView hintText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inputcell_picture, container);
        choose = (ImageView) view.findViewById(R.id.iv_choose);
        //labelText = (TextView) view.findViewById(R.id.tv_clabel);
        hintText = (TextView) view.findViewById(R.id.tv_hint);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageViewClicked();
            }
        });
        return view;
    }

    public void setHintText(String hint) {
        this.hintText.setHint(hint);
    }

    public void setLabelText(String labelText) {
      //  this.labelText.setText(labelText);
    }

    public void onImageViewClicked() {
        String[] items = {"拍照", "相册"};
        new AlertDialog.Builder(getActivity()).setTitle(hintText.getText()).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        takePhoto();
                        break;
                    case 1:
                        pickFormAlbum();
                        break;
                }
            }


        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bmp;
        if (requestCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == REQURSTCODE_CAMERA) {
            bmp = (Bitmap) data.getExtras().get("data");
            choose.setImageBitmap(bmp);
        } else if (requestCode == REQURSTCODE_ALBUM) {
            try {
                bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                choose.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    void pickFormAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }
}
