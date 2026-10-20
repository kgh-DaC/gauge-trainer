package io.github.kghdac.gaugetrainer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

/**
 * 앱의 유일한 화면.
 * 화면 전체를 "웹뷰(앱 안의 작은 브라우저)"로 채우고,
 * 앱 안에 들어 있는 index.html(계측기 5종 통합 페이지)을 띄운다.
 */
public class MainActivity extends Activity {

    // 앱 안에 복사된 웹 페이지 위치 (app/build.gradle.kts 의 syncWebAssets 작업이 넣어 준다)
    private static final String START_PAGE = "file:///android_asset/www/index.html";

    private FrameLayout root;   // 웹뷰를 감싸는 바깥 틀(시스템 영역만큼 여백을 주는 용도)
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1) 바깥 틀 안에 웹뷰를 넣고 화면에 꽉 차게 붙인다
        root = new FrameLayout(this);
        root.setBackgroundColor(Color.WHITE);
        webView = new WebView(this);
        webView.setBackgroundColor(Color.WHITE);
        root.addView(webView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        setContentView(root);

        // 2) 웹뷰 설정
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);   // 눈금을 드래그하고 정답을 계산하는 기능은 자바스크립트로 동작한다
        s.setDomStorageEnabled(true);   // 웹 페이지가 간단한 값을 저장할 수 있게 허용
        s.setTextZoom(100);             // 휴대폰 글꼴 크기 설정 때문에 눈금 글자가 커져 화면이 깨지는 것을 막는다
        s.setSupportZoom(false);        // 두 손가락 확대를 끈다(드래그 조작과 헷갈리지 않게)

        // 페이지 안에서 링크를 눌러도 외부 브라우저로 나가지 않고 앱 안에서 처리
        webView.setWebViewClient(new WebViewClient());

        // 3) 상태표시줄·내비게이션바(화면 위/아래의 시스템 영역)에 계측기가 가려지지 않게 여백을 준다
        fitInsideSystemBars();

        // 4) 페이지 열기 (화면을 돌린 뒤 다시 만들어진 경우에는 이전 상태를 되살린다)
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            webView.loadUrl(START_PAGE);
        }
    }

    /**
     * 안드로이드 11(API 30) 이상에서는 앱 화면이 상태표시줄 뒤까지 늘어난다(특히 안드로이드 15 이상은 강제).
     * 그래서 시스템 영역의 크기만큼 바깥 틀 안쪽에 여백(padding)을 준다.
     * 그보다 오래된 안드로이드는 시스템이 알아서 피해 주므로 아무것도 하지 않는다.
     */
    private void fitInsideSystemBars() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) return;

        getWindow().setDecorFitsSystemWindows(false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        // 배경이 흰색이므로 시스템 아이콘(시계, 배터리 등)을 어두운 색으로
        WindowInsetsController c = getWindow().getInsetsController();
        if (c != null) {
            int light = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    | WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS;
            c.setSystemBarsAppearance(light, light);
        }

        root.setOnApplyWindowInsetsListener((v, insets) -> {
            // systemBars: 상태표시줄+내비게이션바, displayCutout: 카메라 구멍(노치)
            Insets bars = insets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    // 화면을 돌리거나 잠시 다른 앱으로 갔을 때 현재 페이지 상태를 저장
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }
}
