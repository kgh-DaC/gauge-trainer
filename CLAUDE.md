## 하네스: 정밀측정 눈금읽기 연습 도구

**목표:** 버니어캘리퍼스·외측/내측 마이크로미터·다이얼게이지·하이트게이지의 눈금을 마우스로 움직여 읽고 '정답확인'으로 검증하는 노션 임베드용 웹 도구를 만든다.

**호출 조건:** 눈금읽기 도구 제작, 계측기 추가·수정, 정답/눈금 오류 수정, 노션 올리기 작업을 요청받으면 `gauge-trainer` 스킬을 사용한다. 단순 질문(눈금 읽는 법 등)에는 직접 답해도 된다.

**변경 이력:**
| 날짜 | 변경 내용 | 대상 | 사유 |
| --- | --- | --- | --- |
| 2026-10-14 | Harness v2로 처음 구성 | 전체 | - |
| 2026-10-15 | 참조사진 기반 외형 개선, 학교 로고·문구 삽입, 버니어 확대 x1 고정, 하이트게이지 돋보기 제거, 외측 슬리브 눈금 아래쪽만, 내측 딤블/슬리브 비율 수정 | 전체 위젯 | 사용자 피드백 |
| 2026-10-18 | 하이트게이지에 고배율 확대창 추가(슬라이더는 고정, 보는 위치만 드래그/휠/키보드로 이동) | height 위젯 | 사용자 피드백: 일치선 찾기 어려움 |
| 2026-10-20 | 안드로이드 앱(WebView) 프로젝트 `android/`와 APK/AAB 자동 빌드 워크플로 추가 | android, .github | 사용자 요청: 안드로이드 앱 배포 |

**작업 위치 (2026-10-18~):** 이 폴더가 깃허브 레포(`kgh-DaC/gauge-trainer`)이며 작업 기준이다. 위젯 원본은 루트의 `vernier.html` `outside-mic.html` `inside-mic.html` `dial.html` `height.html`(= 예전 `_workspace/02_widget_*.html`)이고, `index.html`은 이 5개를 탭으로 묶은 통합본이다. 통합본은 `node .claude/skills/gauge-trainer/scripts/build.js`(레포 루트에서 실행)로 루트의 위젯 5개를 읽어 `index.html`을 다시 만든다. 푸시하면 GitHub Pages(https://kgh-dac.github.io/gauge-trainer/)에 반영된다.

**안드로이드 앱 (2026-10-20~):** `android/`는 빌드 때 루트의 `index.html`을 앱 assets로 복사해 오프라인으로 띄우는 WebView 앱이다(위젯을 고치면 앱 코드는 손대지 않아도 된다). `.github/workflows/android.yml`이 푸시마다 debug APK를 Actions Artifacts로, Release 발행 시 APK(서명 Secrets가 있으면 AAB도)를 Release에 붙인다. 안내서는 `docs/ANDROID.md`. 이 클라우드 환경은 dl.google.com이 막혀 로컬 빌드가 안 되므로 빌드 확인은 Actions 결과로 한다.
