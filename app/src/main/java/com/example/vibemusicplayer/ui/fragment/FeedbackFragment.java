package com.example.vibemusicplayer.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.vibemusicplayer.MyApplication;
import com.example.vibemusicplayer.R;
import com.example.vibemusicplayer.ui.util.GlideEngine;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

public class FeedbackFragment extends Fragment {

    private ImageView image;
    private Button submit;
    private String feedback, imagePath, imageRealPath;
    private EditText editText;

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        editText = view.findViewById(R.id.feedback_editText);
        image = view.findViewById(R.id.feedback_image);
        submit = view.findViewById(R.id.button_submit);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 监听 editText 文本变化
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                feedback = editText.getText().toString().trim();
            }

        });

        // 选择图片
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在点击事件中打开图片选择器
                PictureSelector.create(requireActivity())
                        .openGallery(SelectMimeType.ofImage())
                        .setImageEngine(GlideEngine.createGlideEngine())
                        .setMaxSelectNum(1) // 设置最大选择数量为1
                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(ArrayList<LocalMedia> result) {
                                if (!result.isEmpty()) {
                                    LocalMedia media = result.get(0); // 获取选中的第一张图片的信息
                                    imagePath = media.getPath();
                                    imageRealPath = media.getRealPath();
                                    Glide.with(requireActivity())
                                            .load(imagePath) // 加载选中的图片
                                            .into(image);

                                    Log.i("ImagePath", "选中图片的路径：" + imagePath);
                                    Log.i("ImagePath", "选中图片的真实路径：" + imageRealPath);
                                }
                            }

                            @Override
                            public void onCancel() {
                                // 点击取消
                            }
                        });
            }
        });

        // 通过 post 请求，将数据提交到 MySql 数据库
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(feedback)) {
                    Toast.makeText(getActivity(), "请填写问题描述", Toast.LENGTH_LONG).show();
                } else if (imagePath == null) {
                    Toast.makeText(getActivity(), "请上传图片", Toast.LENGTH_LONG).show();
                } else {
                    // 把数据写进数据库
                    String url = new MyApplication().insertUrl;
                    RequestParams params = new RequestParams(url);

                    // post
                    params.setMultipart(true);
                    params.addBodyParameter("content", feedback);
                    params.addBodyParameter("uploadFile", new File(imageRealPath));

                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                            navController.navigate(R.id.nav_home);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(x.app(), ex.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            Toast.makeText(x.app(), "已取消", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }

            }
        });

    }

}