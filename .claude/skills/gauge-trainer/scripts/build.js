// 사용법: node build.js [프로젝트루트]   (생략하면 이 스크립트 기준 레포 루트)
// 레포 루트의 vernier.html, outside-mic.html, inside-mic.html, dial.html, height.html 5종을 읽어
// 탭형 통합 페이지 index.html을 같은 루트에 만든다(GitHub Pages가 이 파일을 연다).
// 위젯끼리 CSS·전역 이름이 충돌하지 않도록 탭마다 iframe(srcdoc)으로 격리한다.
const fs = require('fs');
const path = require('path');

const root = process.argv[2] || path.resolve(__dirname, '..', '..', '..', '..');
const OUT_FILE = 'index.html';

const TABS = [
  { id: 'vernier', label: '버니어캘리퍼스' },
  { id: 'outside-mic', label: '외측마이크로미터' },
  { id: 'inside-mic', label: '내측마이크로미터' },
  { id: 'dial', label: '다이얼게이지' },
  { id: 'height', label: '하이트게이지' },
];

const widgets = {};
for (const t of TABS) {
  const src = path.join(root, `${t.id}.html`);
  if (!fs.existsSync(src)) { console.warn(`누락: ${src} (탭에서 제외)`); continue; }
  const html = fs.readFileSync(src, 'utf8').replace(/\r\n/g, '\n');   // Windows 체크아웃의 CRLF를 LF로 통일
  widgets[t.id] = html;
}
const tabs = TABS.filter((t) => widgets[t.id]);

// </script> 가 문자열 안에 있어도 페이지가 깨지지 않도록 이스케이프
const data = JSON.stringify(widgets).replace(/</g, '\\u003c');

const page = `<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>정밀측정 눈금읽기 연습</title>
<style>
  :root { --bg:#fff; --fg:#1f2328; --muted:#656d76; --line:#d0d7de; --accent:#0969da; }
  @media (prefers-color-scheme: dark) { :root { --bg:#0d1117; --fg:#e6edf3; --muted:#8b949e; --line:#30363d; --accent:#4493f8; } }
  html, body { height:100%; height:100dvh; margin:0; }
  body { display:flex; flex-direction:column; background:var(--bg); color:var(--fg); font:15px/1.4 system-ui, "Malgun Gothic", sans-serif; }
  nav { display:flex; flex-wrap:wrap; gap:4px; padding:8px 12px 0; border-bottom:1px solid var(--line); flex:none; }
  nav button { font:inherit; padding:6px 10px; border:1px solid transparent; border-bottom:none; border-radius:8px 8px 0 0; background:transparent; color:var(--muted); cursor:pointer; white-space:nowrap; }
  nav button[aria-selected="true"] { color:var(--fg); border-color:var(--line); background:var(--bg); font-weight:600; box-shadow:0 2px 0 var(--bg); position:relative; top:1px; }
  main { flex:1; min-height:0; }
  iframe { width:100%; height:100%; border:0; display:none; }
  iframe.on { display:block; }
</style>
</head>
<body>
<nav role="tablist" id="tabs"></nav>
<main id="panes"></main>
<script>
(function () {
  var WIDGETS = ${data};
  var TABS = ${JSON.stringify(tabs)};
  var nav = document.getElementById('tabs'), panes = document.getElementById('panes');
  var frames = {}, buttons = {};

  function show(id) {
    TABS.forEach(function (t) {
      var on = t.id === id;
      buttons[t.id].setAttribute('aria-selected', on ? 'true' : 'false');
      if (frames[t.id]) frames[t.id].classList.toggle('on', on);
    });
    if (!frames[id]) {  // 처음 열 때만 불러온다
      var f = document.createElement('iframe');
      f.title = id;
      f.srcdoc = WIDGETS[id];
      f.className = 'on';
      panes.appendChild(f);
      frames[id] = f;
    }
    try { location.hash = id; } catch (e) {}
  }

  TABS.forEach(function (t) {
    var b = document.createElement('button');
    b.setAttribute('role', 'tab');
    b.textContent = t.label;
    b.onclick = function () { show(t.id); };
    nav.appendChild(b);
    buttons[t.id] = b;
  });
  var start = (location.hash || '').slice(1);
  show(WIDGETS[start] ? start : TABS[0].id);
})();
</script>
</body>
</html>
`;
fs.writeFileSync(path.join(root, OUT_FILE), page);
console.log(`완료: ${OUT_FILE} (${tabs.length}개 탭)`);
