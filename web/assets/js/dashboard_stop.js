/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

(function () {
    var app = document.getElementById('pfApp');
    var $ = function (s, r) { return (r || document).querySelector(s); };
    var $$ = function (s, r) { return Array.prototype.slice.call((r || document).querySelectorAll(s)); };

    /* ---------- Tema (recordado) ---------- */
    function setTheme(t) {
        app.setAttribute('data-theme', t);
        $('#themeBtn i').className = t === 'dark' ? 'bi bi-sun-fill' : 'bi bi-moon-stars-fill';
        try { localStorage.setItem('pf-theme', t); } catch (e) {}
        if (window.pfCharts) { window.pfCharts.forEach(function (c) { c.options.color = textColor(); applyScale(c); c.update(); }); }
    }
    function textColor() { return app.getAttribute('data-theme') === 'dark' ? '#8a9bb5' : '#64748b'; }
    function gridColor() { return app.getAttribute('data-theme') === 'dark' ? '#1e2b45' : '#e2e8f0'; }
    function applyScale(c) {
        if (!c.options.scales || !c.options.scales.x) return;
        ['x', 'y'].forEach(function (a) {
            c.options.scales[a].ticks.color = textColor();
            c.options.scales[a].grid.color = gridColor();
        });
    }
    var saved = 'light';
    try { saved = localStorage.getItem('pf-theme') || (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'); } catch (e) {}
    $('#themeBtn').addEventListener('click', function () { setTheme(app.getAttribute('data-theme') === 'dark' ? 'light' : 'dark'); });

    /* ---------- Saludo y fecha ---------- */
    var h = new Date().getHours();
    $('#saludo').textContent = (h < 12 ? 'Buenos días' : h < 19 ? 'Buenas tardes' : 'Buenas noches') + ', administrador';
    $('#fechaHoy').textContent = new Date().toLocaleDateString('es-PA', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' });

    /* ---------- Menú móvil (con fondo oscuro) y scrollspy ---------- */
    var side = $('#pfSide'), overlay = $('#pfOverlay');
    function toggleMenu(open) {
        var o = typeof open === 'boolean' ? open : !side.classList.contains('open');
        side.classList.toggle('open', o);
        overlay.classList.toggle('show', o);
    }
    $('#menuBtn').addEventListener('click', function () { toggleMenu(); });
    overlay.addEventListener('click', function () { toggleMenu(false); });
    window.addEventListener('resize', function () { if (window.innerWidth > 991) toggleMenu(false); });
    $$('.pf-nav a').forEach(function (a) { a.addEventListener('click', function () { toggleMenu(false); }); });
    var links = $$('.pf-nav a');
    var obs = new IntersectionObserver(function (es) {
        es.forEach(function (e) {
            if (e.isIntersecting) {
                links.forEach(function (l) { l.classList.toggle('active', l.getAttribute('href') === '#' + e.target.id); });
            }
        });
    }, { rootMargin: '-30% 0px -60% 0px' });
    ['resumen', 'registros', 'adopciones'].forEach(function (id) { var el = document.getElementById(id); if (el) obs.observe(el); });

    /* ---------- Contadores animados ---------- */
    $$('[data-count]').forEach(function (el) {
        var end = parseInt(el.getAttribute('data-count'), 10) || 0, t0 = null;
        function step(ts) {
            if (!t0) t0 = ts;
            var p = Math.min((ts - t0) / 900, 1);
            el.textContent = Math.round(end * (1 - Math.pow(1 - p, 3)));
            if (p < 1) requestAnimationFrame(step);
        }
        requestAnimationFrame(step);
    });

    /* ---------- Tasa de adopción ---------- */
    var disp = parseInt($('.k1 .val').getAttribute('data-count'), 10) || 0;
    var adop = parseInt($('.k2 .val').getAttribute('data-count'), 10) || 0;
    var total = disp + adop, pct = total ? Math.round(adop * 100 / total) : 0;
    $('#ring').style.setProperty('--p', pct);
    $('#ringTxt').textContent = pct + '%';
    $('#ringSub').textContent = adop + ' de ' + total + ' mascotas encontraron hogar';

    /* ---------- Gráficos (datos leídos de la tabla, sin tocar el backend) ---------- */
    var rows = $$('#tblReg tbody tr');
    var porMes = {}, porEspecie = {};
    var cnt = { Pendiente: 0, Aprobado: 0, Rechazado: 0 };
    rows.forEach(function (r) {
        var f = (r.getAttribute('data-fecha') || '').trim();
        var m = /^\d{4}-\d{2}/.test(f) ? f.substring(0, 7) : 'Sin fecha';
        porMes[m] = (porMes[m] || 0) + 1;
        var e = (r.getAttribute('data-especie') || 'Otro').trim() || 'Otro';
        porEspecie[e] = (porEspecie[e] || 0) + 1;
        var st = r.getAttribute('data-estado');
        if (cnt[st] !== undefined) cnt[st]++;
    });
    var meses = Object.keys(porMes).sort();
    var nombresMes = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
    var etMes = meses.map(function (m) { return /^\d{4}-\d{2}$/.test(m) ? nombresMes[parseInt(m.substring(5), 10) - 1] + ' ' + m.substring(2, 4) : m; });

    Chart.defaults.font.family = 'Inter, system-ui, sans-serif';
    Chart.defaults.color = textColor();
    window.pfCharts = [];

    var ctxMes = $('#chMes').getContext('2d');
    var grad = ctxMes.createLinearGradient(0, 0, 0, 260);
    grad.addColorStop(0, 'rgba(14,165,233,.45)'); grad.addColorStop(1, 'rgba(14,165,233,0)');
    var cMes = new Chart(ctxMes, {
        type: 'line',
        data: { labels: etMes, datasets: [{ label: 'Solicitudes', data: meses.map(function (m) { return porMes[m]; }),
            borderColor: '#0ea5e9', backgroundColor: grad, fill: true, tension: .4, pointRadius: 5, pointBackgroundColor: '#0ea5e9', borderWidth: 3 }] },
        options: { responsive: true, maintainAspectRatio: false, plugins: { legend: { display: false } },
            scales: { x: { grid: { display: false }, ticks: { color: textColor() } },
                      y: { beginAtZero: true, ticks: { precision: 0, color: textColor() }, grid: { color: gridColor() } } } }
    });
    var cEst = new Chart($('#chEstado'), {
        type: 'doughnut',
        data: { labels: ['Pendiente', 'Aprobado', 'Rechazado'], datasets: [{ data: [cnt.Pendiente, cnt.Aprobado, cnt.Rechazado],
            backgroundColor: ['#f59e0b', '#10b981', '#ef4444'], borderWidth: 0, hoverOffset: 8 }] },
        options: { responsive: true, maintainAspectRatio: false, cutout: '68%', plugins: { legend: { position: 'bottom' } } }
    });
    var esp = Object.keys(porEspecie);
    var cEsp = new Chart($('#chEspecie'), {
        type: 'bar',
        data: { labels: esp, datasets: [{ data: esp.map(function (k) { return porEspecie[k]; }),
            backgroundColor: ['#6366f1', '#0ea5e9', '#10b981', '#f59e0b', '#ec4899', '#14b8a6'], borderRadius: 8 }] },
        options: { responsive: true, maintainAspectRatio: false, plugins: { legend: { display: false } },
            scales: { x: { grid: { display: false }, ticks: { color: textColor() } },
                      y: { beginAtZero: true, ticks: { precision: 0, color: textColor() }, grid: { color: gridColor() } } } }
    });
    window.pfCharts = [cMes, cEst, cEsp];
    setTheme(saved);

    /* ---------- Tablas: búsqueda, filtros, paginación, CSV ---------- */
    var PER = 8;
    function initTable(id) {
        var tbl = document.getElementById(id), tb = tbl.tBodies[0];
        var all = $$('tr', tb), state = { q: '', f: '', page: 1 };
        var pager = $('[data-pager="' + id + '"]');
        var empty = document.createElement('tr');
        empty.className = 'empty-row';
        empty.innerHTML = '<td colspan="5">🐾 No hay resultados para mostrar</td>';
        function render() {
            var vis = all.filter(function (r) {
                var okF = !state.f || r.getAttribute('data-estado') === state.f;
                var okQ = !state.q || r.textContent.toLowerCase().indexOf(state.q) > -1;
                return okF && okQ;
            });
            var pages = Math.max(1, Math.ceil(vis.length / PER));
            if (state.page > pages) state.page = pages;
            var ini = (state.page - 1) * PER;
            all.forEach(function (r) { r.style.display = 'none'; });
            vis.slice(ini, ini + PER).forEach(function (r) { r.style.display = ''; });
            if (empty.parentNode) tb.removeChild(empty);
            if (!vis.length) tb.appendChild(empty);
            var html = '<span>' + vis.length + ' registro(s)</span><div class="pg">';
            html += '<button data-p="' + (state.page - 1) + '"' + (state.page === 1 ? ' disabled' : '') + '>‹</button>';
            for (var i = 1; i <= pages; i++) html += '<button data-p="' + i + '"' + (i === state.page ? ' class="on"' : '') + '>' + i + '</button>';
            html += '<button data-p="' + (state.page + 1) + '"' + (state.page === pages ? ' disabled' : '') + '>›</button></div>';
            pager.innerHTML = html;
        }
        pager.addEventListener('click', function (e) {
            var b = e.target.closest('button[data-p]');
            if (b && !b.disabled) { state.page = parseInt(b.getAttribute('data-p'), 10); render(); }
        });
        $('[data-search="' + id + '"]').addEventListener('input', function (e) { state.q = e.target.value.toLowerCase().trim(); state.page = 1; render(); });
        var chips = $('.chips[data-for="' + id + '"]');
        chips.addEventListener('click', function (e) {
            var b = e.target.closest('.chip'); if (!b) return;
            $$('.chip', chips).forEach(function (c) { c.classList.remove('on'); });
            b.classList.add('on'); state.f = b.getAttribute('data-f'); state.page = 1; render();
        });
        $('[data-csv="' + id + '"]').addEventListener('click', function (e) {
            var name = e.currentTarget.getAttribute('data-name');
            var lines = [$$('th', tbl).slice(0, 4).map(function (th) { return th.textContent.trim(); })];
            all.filter(function (r) {
                return (!state.f || r.getAttribute('data-estado') === state.f) && (!state.q || r.textContent.toLowerCase().indexOf(state.q) > -1);
            }).forEach(function (r) {
                lines.push($$('td', r).slice(0, 4).map(function (td) { return td.textContent.replace(/\s+/g, ' ').trim(); }));
            });
            var csv = lines.map(function (l) { return l.map(function (c) { return '"' + c.replace(/"/g, '""') + '"'; }).join(','); }).join('\n');
            var a = document.createElement('a');
            a.href = URL.createObjectURL(new Blob(['\ufeff' + csv], { type: 'text/csv;charset=utf-8' }));
            a.download = 'patitas-' + name + '.csv'; a.click();
            toast('Exportado: patitas-' + name + '.csv');
        });
        render();
    }
    initTable('tblReg'); initTable('tblAdop');

    function toast(msg) {
        var t = $('#toastPf'); t.textContent = msg; t.classList.add('show');
        setTimeout(function () { t.classList.remove('show'); }, 2600);
    }
})();


