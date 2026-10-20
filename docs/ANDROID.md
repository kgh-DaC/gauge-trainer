# 안드로이드 앱 만들기·올리기 안내

정밀측정 눈금읽기 연습 도구(`index.html`)를 **안드로이드 앱**으로 감싼 프로젝트입니다.
앱 안에 계측기 화면이 통째로 들어가므로 **인터넷이 없어도** 동작합니다.

## 1. 어떻게 만들어지나요?

```
레포 루트의 위젯 5개 (vernier.html …)
        │  build.js 가 하나로 묶음
        ▼
index.html (계측기 5종 탭 통합본)
        │  앱을 빌드할 때 자동 복사
        ▼
android/ 앱 (화면 전체가 '웹뷰' = 앱 속 작은 브라우저)
        │  깃허브 Actions 가 자동 빌드
        ▼
APK(휴대폰 설치 파일) / AAB(플레이스토어 업로드 파일)
```

- 위젯을 고쳐서 푸시하면 **앱도 자동으로 다시 만들어집니다.** 앱 코드를 따로 고칠 필요가 없습니다.
- 내 PC에 안드로이드 스튜디오를 설치하지 않아도 됩니다. 깃허브 서버가 빌드합니다.

## 2. 폴더 구성

| 파일 | 하는 일 |
| --- | --- |
| `android/app/src/main/java/.../MainActivity.java` | 앱 화면. 웹뷰를 띄우고 `index.html`을 연다 |
| `android/app/src/main/AndroidManifest.xml` | 앱 이름·아이콘·첫 화면 등록 |
| `android/app/build.gradle.kts` | 앱 버전, 서명, `index.html` 복사 설정 |
| `android/app/src/main/res/values/strings.xml` | 홈 화면에 보이는 앱 이름(`눈금읽기 연습`) |
| `android/app/src/main/res/drawable/ic_launcher_foreground.xml` | 앱 아이콘 그림(버니어캘리퍼스) |
| `.github/workflows/android.yml` | 깃허브가 APK/AAB를 자동으로 만드는 순서 |

## 3. 테스트용 APK 받아서 휴대폰에 설치하기 (플레이스토어 없이)

### 가장 쉬운 방법: 고정 링크 (로그인 필요 없음)

👉 **https://github.com/kgh-DaC/gauge-trainer/releases/download/test-latest/gauge-trainer-debug.apk**

- 휴대폰 크롬에서 열면 바로 APK가 내려받아집니다. 눌러서 설치하세요.
- 앱을 고쳐서 푸시할 때마다 **같은 주소의 파일이 최신으로 바뀝니다.** QR코드로 만들어 두면 계속 쓸 수 있습니다.
- 깃허브 Releases 목록에 "테스트용 최신 APK"(Pre-release)로 보이며, 빌드 시각·커밋이 적혀 있습니다.

### 다른 방법: Actions 실행 기록에서 받기 (깃허브 로그인 필요)

1. 깃허브 레포 → **Actions** 탭 → 왼쪽 **Android 앱 빌드** → 맨 위(가장 최근) 실행 기록 클릭
2. 페이지 맨 아래 **Artifacts**의 `gauge-trainer-android-…` 를 눌러 zip 내려받기
3. zip 안의 `gauge-trainer-debug.apk` 를 휴대폰으로 옮기기(카카오톡 나에게 보내기, 구글 드라이브 등)
4. 휴대폰에서 APK를 눌러 설치 → "출처를 알 수 없는 앱 설치 허용"을 묻으면 허용

> 학생들에게 나눠 줄 때는 4번의 **Release** 방식이 링크 하나로 받을 수 있어 편합니다.

## 4. 학생 배포용 링크 만들기 (Release)

1. 깃허브 레포 → 오른쪽 **Releases** → **Draft a new release**
2. **Choose a tag** 에 `v1.0.0` 처럼 입력 → **Create new tag**
3. 제목(예: `눈금읽기 연습 1.0.0`)을 쓰고 **Publish release**
4. 3~5분 뒤 그 Release 페이지에 APK 파일이 자동으로 붙습니다.
   항상 최신 파일을 가리키는 고정 링크(QR코드로 만들어 배포하기 좋음):
   - 서명 키 등록 전: `https://github.com/kgh-DaC/gauge-trainer/releases/latest/download/gauge-trainer-debug.apk`
   - 서명 키 등록 후: `https://github.com/kgh-DaC/gauge-trainer/releases/latest/download/gauge-trainer.apk`

다음 버전을 낼 때는 태그 숫자만 올리면 됩니다(`v1.0.1`, `v1.1.0` …).

## 5. 구글 플레이스토어에 올리기

### 5-1. 처음 한 번만: 서명 키(업로드 키) 만들기

플레이스토어는 "이 앱을 만든 사람이 맞다"는 증명으로 **서명 키**를 요구합니다.
PC에 자바가 설치되어 있으면 명령 프롬프트에서 아래 한 줄로 만듭니다.

```bash
keytool -genkeypair -v -keystore upload.jks -alias upload -keyalg RSA -keysize 2048 -validity 10000
```

- 비밀번호를 두 번 묻고, 이름·기관 등을 묻습니다(학교/학과 이름 입력).
- 만들어진 `upload.jks` 와 비밀번호는 **잃어버리면 앱 업데이트가 어려워집니다.** USB·클라우드 두 곳에 보관하세요.
- **이 파일을 레포에 올리면 안 됩니다.** (`.gitignore`로 막아 두었습니다)

### 5-2. 처음 한 번만: 깃허브에 서명 키 등록

1. 키 파일을 글자로 바꾸기
   - Windows PowerShell: `[Convert]::ToBase64String([IO.File]::ReadAllBytes("upload.jks")) | Set-Clipboard` (클립보드에 복사됨)
   - Mac/리눅스: `base64 -i upload.jks | pbcopy` 또는 `base64 -w0 upload.jks`
2. 깃허브 레포 → **Settings → Secrets and variables → Actions → New repository secret** 에서 4개 등록

| 이름 | 값 |
| --- | --- |
| `ANDROID_KEYSTORE_BASE64` | 1번에서 복사한 긴 글자 |
| `ANDROID_KEYSTORE_PASSWORD` | 키스토어 비밀번호 |
| `ANDROID_KEY_ALIAS` | `upload` |
| `ANDROID_KEY_PASSWORD` | 키 비밀번호(위 명령에서 따로 묻지 않았으면 키스토어 비밀번호와 같음) |

등록 후 Release를 발행하면 `gauge-trainer.aab`(플레이스토어용)와 `gauge-trainer.apk`(서명된 설치 파일)가 함께 만들어집니다.

### 5-3. 플레이 콘솔에 올리기

1. https://play.google.com/console 에서 개발자 계정 만들기(1회 등록비 25달러, 학교 명의면 '조직' 계정)
2. **앱 만들기** → 이름 `눈금읽기 연습`, 앱/무료 선택
3. **테스트 → 내부 테스트**(또는 비공개 테스트)에서 새 버전 만들기 → `gauge-trainer.aab` 업로드
4. 스토어 등록정보 작성: 짧은 설명, 아이콘(512×512 PNG), 스크린샷(`docs/*.png` 활용 가능)
5. 앱 콘텐츠 설문: 개인정보처리방침(수집 정보 없음), 광고 없음, 대상 연령, 데이터 보안(수집 없음)
6. 검토 제출

> **참고:** 2023년 11월 이후 만든 **개인** 개발자 계정은 정식 출시 전에
> "테스터 12명 이상이 14일 동안 비공개 테스트"를 해야 합니다. 수업 학생들을 테스터로 등록하면 해결됩니다.
> 조직(학교) 계정은 이 조건이 없습니다.

### 이 앱이 수집하는 정보

없습니다. 인터넷 권한도 없고, 앱 안의 화면만 보여 줍니다. 데이터 보안 설문에서 "수집하지 않음"으로 답하면 됩니다.

## 6. 자주 바꾸는 것

| 바꾸고 싶은 것 | 고칠 곳 |
| --- | --- |
| 앱 이름 | `android/app/src/main/res/values/strings.xml` 의 `app_name` |
| 아이콘 배경색 | `android/app/src/main/res/values/colors.xml` |
| 아이콘 그림 | `android/app/src/main/res/drawable/ic_launcher_foreground.xml` |
| 계측기 화면 | 루트의 위젯 html 수정 → `node .claude/skills/gauge-trainer/scripts/build.js` → 푸시 |

`applicationId`(`io.github.kghdac.gaugetrainer`)는 플레이스토어에 한 번 올리면 **바꿀 수 없으니** 그대로 두세요.
