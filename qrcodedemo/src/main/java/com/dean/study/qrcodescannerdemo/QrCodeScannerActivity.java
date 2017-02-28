package com.dean.study.qrcodescannerdemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by TianWei on 2016/3/9.
 */
public class QrCodeScannerActivity extends Activity implements ZXingScannerView.ResultHandler, View.OnClickListener {

    private static final int PICK_PHOTO = 1001;
    private ZXingScannerView mQrCodeScannerView;
    private TextView mPhotoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        mQrCodeScannerView = (ZXingScannerView) findViewById(R.id.ui_qrcode_scanner_ZXingScannerView);
        mQrCodeScannerView.startCamera();
        mQrCodeScannerView.setResultHandler(this);
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        mQrCodeScannerView.setFormats(formats);
        mPhotoTextView = (TextView) findViewById(R.id.textview_photo);
        mPhotoTextView.setOnClickListener(this);

    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d("QRCODE", rawResult.getText());
        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_photo:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_PHOTO:
                if (data != null) {
                    Uri uri = data.getData();
                    handlePhotoResult(uri);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQrCodeScannerView.stopCamera();
    }

    private void handlePhotoResult(Uri uri) {
        String path = getImagePathFromUri(uri);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        //                opts.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        // 解码的参数
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>(2);

        // 可以解析的编码类型
        List<BarcodeFormat> decodeFormats = new ArrayList<BarcodeFormat>();
        decodeFormats.add(BarcodeFormat.CODABAR);
        decodeFormats.add(BarcodeFormat.CODE_39);
        decodeFormats.add(BarcodeFormat.CODE_93);
        decodeFormats.add(BarcodeFormat.CODE_128);
        decodeFormats.add(BarcodeFormat.DATA_MATRIX);
        decodeFormats.add(BarcodeFormat.EAN_8);
        decodeFormats.add(BarcodeFormat.EAN_13);
        decodeFormats.add(BarcodeFormat.ITF);
        decodeFormats.add(BarcodeFormat.MAXICODE);
        decodeFormats.add(BarcodeFormat.PDF_417);
        decodeFormats.add(BarcodeFormat.QR_CODE);
        decodeFormats.add(BarcodeFormat.RSS_14);
        decodeFormats.add(BarcodeFormat.RSS_EXPANDED);
        decodeFormats.add(BarcodeFormat.UPC_A);
        decodeFormats.add(BarcodeFormat.UPC_E);
        decodeFormats.add(BarcodeFormat.UPC_EAN_EXTENSION);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        // 设置继续的字符编码格式为UTF8
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8");

        // 设置解析配置参数
        multiFormatReader.setHints(hints);

        // 开始对图像资源解码
        com.google.zxing.Result rawResult = null;

        BitmapLum bitmapLum = new BitmapLum(bitmap);
        File file = new File(path);
        try {
            rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(
                    bitmapLum)));
            String resultUrl = rawResult.getText();
            Toast.makeText(this, resultUrl, Toast.LENGTH_SHORT).show();

        } catch (NotFoundException e) {
            e.printStackTrace();
            if (file != null && file.exists()) {
                Toast.makeText(this, "您选择的不是二维码图片", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private String getImagePathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    class BitmapLum extends LuminanceSource {
        private byte[] bitmapPixels;

        protected BitmapLum(Bitmap bitmap) {
            super(bitmap.getWidth(), bitmap.getHeight());
            int width = bitmap.getWidth(); int height = bitmap.getHeight();
            int[] data = new int[width * height];
            this.bitmapPixels = new byte[width * height];
            bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());
            for (int i = 0; i < data.length; i++) {
                this.bitmapPixels[i] = (byte) data[i];
            }
        }

        @Override
        public byte[] getRow(int i, byte[] bytes) {
            // 得到像素数据
            System.arraycopy(bitmapPixels, i * getWidth(), bytes, 0, getWidth());
            return bytes;
        }

        @Override
        public byte[] getMatrix() {

            return bitmapPixels;
        }
    }
}
