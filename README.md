# 정밀측정 눈금읽기 연습

**한국폴리텍대학 광주캠퍼스 기계시스템과** 정밀측정 수업용 눈금읽기 연습 도구입니다.
계측기의 슬라이더·심블·바늘을 마우스(또는 손가락)로 움직여 눈금을 읽고, **정답확인** 버튼으로 값과 계산 과정을 확인합니다.
웹브라우저에서 바로 쓰거나, 안드로이드 앱으로 설치해 **인터넷 없이** 쓸 수 있습니다.

## 🔗 바로 사용하기

<table>
<tr>
<th>💻 웹에서 바로 사용</th>
<th>📱 안드로이드 앱 설치 (테스트용)</th>
</tr>
<tr>
<td align="center"><img src="docs/qr-web.png" width="200" alt="웹사이트 QR코드"></td>
<td align="center"><img src="docs/qr-test-apk.png" width="200" alt="안드로이드 앱 설치 파일 QR코드"></td>
</tr>
<tr>
<td align="center"><a href="https://kgh-dac.github.io/gauge-trainer/">kgh-dac.github.io/gauge-trainer</a></td>
<td align="center"><a href="https://github.com/kgh-DaC/gauge-trainer/releases/download/test-latest/gauge-trainer-debug.apk">gauge-trainer-debug.apk 내려받기</a></td>
</tr>
<tr>
<td>PC·태블릿·휴대폰 브라우저에서 열면 됩니다.</td>
<td>휴대폰 카메라로 찍으면 설치 파일이 내려받아집니다.<br>앱을 고칠 때마다 같은 링크의 파일이 최신으로 바뀝니다.</td>
</tr>
</table>

> 🖨️ 수업에서 나눠 줄 **인쇄용 안내 카드**: [docs/qr-test-apk-card.png](docs/qr-test-apk-card.png)

### 계측기별 페이지 (노션 임베드용)

| 계측기 | 최소눈금 | 단독 페이지 |
| --- | --- | --- |
| 버니어캘리퍼스 | 0.05mm / 0.02mm | [vernier.html](https://kgh-dac.github.io/gauge-trainer/vernier.html) |
| 외측마이크로미터 | 0.01mm / 0.001mm | [outside-mic.html](https://kgh-dac.github.io/gauge-trainer/outside-mic.html) |
| 내측마이크로미터 | 0.01mm | [inside-mic.html](https://kgh-dac.github.io/gauge-trainer/inside-mic.html) |
| 다이얼게이지 | 0.01mm | [dial.html](https://kgh-dac.github.io/gauge-trainer/dial.html) |
| 하이트게이지 | 0.02mm | [height.html](https://kgh-dac.github.io/gauge-trainer/height.html) |

노션 페이지에서 `/임베드`(또는 `/embed`)를 입력하고 위 주소를 붙여넣으면 페이지 안에 계측기가 들어갑니다.

## 사용 방법
1. 위쪽 탭에서 계측기를 선택합니다.
2. 슬라이더·심블·바늘을 드래그하거나 버튼/키보드로 움직여 눈금을 읽습니다.
3. **정답확인**을 눌러 값과 계산 과정을 확인합니다.
4. **새 문제**를 누르면 무작위 위치에서 다시 연습할 수 있습니다.

### 📱 휴대폰에서 하이트게이지 쓰기
- **파란 부척판**(또는 전체 그림의 슬라이더)을 잡고 끌면 눈금이 움직입니다.
- 그 밖의 곳을 끌면 **화면이 스크롤**됩니다.
- 눈금을 다 맞췄으면 **🔓 눈금 잠금**을 누르세요. 잠긴 동안에는 어디를 끌어도 화면만 움직입니다.

## 📱 안드로이드 앱 설치
1. 위 **앱 QR코드**를 휴대폰 카메라로 찍어 `gauge-trainer-debug.apk`를 내려받습니다.
2. 내려받은 파일을 눌러 설치합니다. "출처를 알 수 없는 앱" 설치를 물으면 **허용**합니다.
3. "Play 프로텍트" 경고가 나오면 **세부정보 → 무시하고 설치**를 누릅니다.
4. 홈 화면의 **눈금읽기 연습** 아이콘으로 실행합니다. (안드로이드 8.0 이상, 인터넷 불필요)

> 이 파일은 **테스트용**입니다. 플레이스토어 등록 방법은 [docs/ANDROID.md](docs/ANDROID.md)를 보세요.

## 화면

### 버니어캘리퍼스
![버니어캘리퍼스](docs/vernier.png)

### 외측마이크로미터
![외측마이크로미터](docs/outside-mic.png)

### 내측마이크로미터
![내측마이크로미터](docs/inside-mic.png)

### 다이얼게이지
![다이얼게이지](docs/dial.png)

### 하이트게이지
![하이트게이지](docs/height.png)

## 구성
| 파일·폴더 | 내용 |
| --- | --- |
| `index.html` | 5종 통합 페이지(탭형). GitHub Pages 첫 화면 |
| `vernier.html` `outside-mic.html` `inside-mic.html` `dial.html` `height.html` | 계측기별 단독 페이지(노션 임베드용) |
| `android/` | `index.html`을 담은 안드로이드 앱(WebView) |
| `.github/workflows/android.yml` | 푸시하면 깃허브가 APK를 자동으로 만들고 위 고정 링크를 갱신 |
| `docs/` | 화면 사진, QR코드, 안드로이드 안내서 |

- 모든 페이지는 외부 라이브러리 없는 단일 HTML(SVG + JS)입니다.
- 위젯 html을 고친 뒤 `node .claude/skills/gauge-trainer/scripts/build.js`로 `index.html`을 다시 만들고 푸시하면, 웹과 앱에 함께 반영됩니다.
