const API = {
  list: '/api/get/cca',
  get: (id) => `/api/get/cca/${id}`,
  update: (id) => `/api/put/cca/${id}`,
  remove: (id) => `/api/delete/cca/${id}`,
  validarCca: (cca) => `/api/validar-cca/${encodeURIComponent(cca)}`
};

function apenasDigitos(valor) {
  return String(valor || '').replace(/\D/g, '');
}

function formatarCca(valor) {
  const v = apenasDigitos(valor).slice(0, 6);
  return v;
}

function aplicarMascaraCca(input) {
  input.value = formatarCca(input.value);
}

function formatarTelefone(valor) {
  const v = apenasDigitos(valor);
  if (!v) return '';
  if (v.length === 10) return v.replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
  if (v.length === 11) return v.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
  return valor || '';
}

function aplicarMascaraTelefone(input) {
  let v = apenasDigitos(input.value).slice(0, 11);
  if (v.length <= 2) input.value = v;
  else if (v.length <= 6) input.value = v.replace(/(\d{2})(\d+)/, '($1) $2');
  else if (v.length <= 10) input.value = v.replace(/(\d{2})(\d{4})(\d+)/, '($1) $2-$3');
  else input.value = v.replace(/(\d{2})(\d{5})(\d+)/, '($1) $2-$3');
}

function escapeHtml(str) {
  return String(str)
    .replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;')
    .replaceAll("'", '&#39;');
}

function setStatus(el, msg, type = '') {
  el.textContent = msg || '';
  el.classList.remove('ok', 'err');
  if (type === 'ok') el.classList.add('ok');
  if (type === 'err') el.classList.add('err');
}

let registros = [];
let registrosFiltrados = [];
let selecionadoId = null;

const tbody = document.getElementById('tbody');
const filtro = document.getElementById('filtro');
const contador = document.getElementById('contador');
const statusLista = document.getElementById('statusLista');
const statusEdicao = document.getElementById('statusEdicao');

const idCcaInput = document.getElementById('idCca');
const ccaInput = document.getElementById('cca');
const principalContatoInput = document.getElementById('principalContato');
const correspondenteInput = document.getElementById('correspondente');
const numeroInput = document.getElementById('numero');

const btnSalvar = document.getElementById('btnSalvar');
const btnExcluir = document.getElementById('btnExcluir');
const btnCancelar = document.getElementById('btnCancelar');

function habilitarEdicao(habilitar) {
  btnSalvar.disabled = !habilitar;
  btnExcluir.disabled = !habilitar;
  btnCancelar.disabled = !habilitar;
}

function limparFormulario() {
  idCcaInput.value = '';
  ccaInput.value = '';
  principalContatoInput.value = '';
  correspondenteInput.value = '';
  numeroInput.value = '';
  selecionadoId = null;
  setStatus(statusEdicao, '');
  habilitarEdicao(false);
  [...tbody.querySelectorAll('tr')].forEach(tr => tr.classList.remove('selected'));
}

function normalizarRegistro(item) {
  return {
    id: item.id ?? item.ID_cca ?? item.idCca ?? '',
    cca: item.cca ?? '',
    correspondente: item.correspondente ?? '',
    principalContato: item.principalContato ?? item.contato ?? '',
    numero: item.numero ?? ''
  };
}

function renderTabela(lista) {
  tbody.innerHTML = '';
  for (const item of lista) {
    const tr = document.createElement('tr');
    tr.dataset.id = item.id;
    tr.innerHTML = `
      <td>${escapeHtml(item.id)}</td>
      <td>${escapeHtml(formatarCca(item.cca))}</td>
      <td>${escapeHtml(item.principalContato)}</td>
      <td>${escapeHtml(item.correspondente)}</td>
      <td>${escapeHtml(formatarTelefone(item.numero))}</td>
    `;
    tr.addEventListener('click', () => selecionarLinha(item.id));
    tbody.appendChild(tr);
  }
  contador.textContent = `${lista.length} registro${lista.length === 1 ? '' : 's'}`;
}

function aplicarFiltro() {
  const q = (filtro.value || '').trim().toLowerCase();
  if (!q) {
    registrosFiltrados = [...registros];
  } else {
    registrosFiltrados = registros.filter(r => {
      return [r.id, r.cca, r.principalContato, r.correspondente, r.numero]
        .join(' ')
        .toLowerCase()
        .includes(q);
    });
  }
  renderTabela(registrosFiltrados);

  if (selecionadoId !== null) {
    const existe = registrosFiltrados.some(r => String(r.id) === String(selecionadoId));
    if (!existe) limparFormulario();
  }
}

function selecionarLinha(id) {
  selecionadoId = id;
  [...tbody.querySelectorAll('tr')].forEach(tr => {
    tr.classList.toggle('selected', tr.dataset.id === String(id));
  });

  const reg = registros.find(r => String(r.id) === String(id));
  if (!reg) return;

  idCcaInput.value = reg.id;
  ccaInput.value = formatarCca(reg.cca);
  principalContatoInput.value = reg.principalContato;
  correspondenteInput.value = reg.correspondente;
  numeroInput.value = formatarTelefone(reg.numero);

  setStatus(statusEdicao, 'Registro carregado. Edite e salve.', 'ok');
  habilitarEdicao(true);
}

function montarPayload() {
  return {
    cca: formatarCca(ccaInput.value),
    correspondente: (correspondenteInput.value || '').trim(),
    principalContato: (principalContatoInput.value || '').trim(),
    numero: apenasDigitos(numeroInput.value)
  };
}

async function validarAntesDeSalvar(payload) {
  if (selecionadoId === null && selecionadoId !== 0) {
    throw new Error('Selecione um registro na tabela para editar.');
  }
  if (!payload.cca || payload.cca.length !== 6) {
    throw new Error('cca deve ter 6 dígitos.');
  }
  if (!payload.principalContato) {
    throw new Error('principalContato é obrigatório.');
  }
  if (!payload.correspondente) {
    throw new Error('correspondente é obrigatório.');
  }
  if (!payload.numero) {
    throw new Error('numero é obrigatório.');
  }
  if (payload.numero.length !== 10 && payload.numero.length !== 11) {
    throw new Error('numero deve ter 10 ou 11 dígitos com DDD.');
  }

  try {
    const resp = await fetch(API.validarCca(payload.cca));
    if (resp.ok) {
      const data = await resp.json().catch(() => null);
      if (data && data.valido === false) {
        throw new Error('cca inválido ou inexistente.');
      }
    }
  } catch (err) {
    // Mantém a tela funcional mesmo que o endpoint de validação não exista.
  }
}

async function carregarLista() {
  setStatus(statusLista, 'Carregando lista...');
  try {
    const resp = await fetch(API.list);
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);

    const data = await resp.json();
    if (!Array.isArray(data)) throw new Error('O endpoint de lista não retornou um array JSON.');

    registros = data.map(normalizarRegistro);
    registrosFiltrados = [...registros];
    renderTabela(registrosFiltrados);
    setStatus(statusLista, `Lista carregada com sucesso (${registros.length}).`, 'ok');
  } catch (err) {
    console.error(err);
    registros = [];
    registrosFiltrados = [];
    renderTabela([]);
    limparFormulario();
    setStatus(statusLista, 'Erro ao carregar a lista. Verifique o endpoint em API.list.', 'err');
  }
}

async function recarregar() {
  limparFormulario();
  await carregarLista();
}

async function salvar() {
  const payload = montarPayload();
  setStatus(statusEdicao, 'Validando...');

  try {
    await validarAntesDeSalvar(payload);
  } catch (err) {
    setStatus(statusEdicao, err.message || 'Dados inválidos.', 'err');
    return;
  }

  setStatus(statusEdicao, 'Salvando alterações...');

  try {
    const resp = await fetch(API.update(selecionadoId), {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (!resp.ok) {
      throw new Error('Erro ao salvar. Verifique o endpoint em API.update.');
    }

    const idx = registros.findIndex(r => String(r.id) === String(selecionadoId));
    if (idx >= 0) {
      registros[idx] = { ...registros[idx], ...payload };
    }

    aplicarFiltro();
    selecionarLinha(selecionadoId);
    setStatus(statusEdicao, 'Salvo com sucesso!', 'ok');
  } catch (err) {
    console.error(err);
    setStatus(statusEdicao, err.message || 'Erro ao salvar.', 'err');
  }
}

function abrirModalExcluir() {
  if (selecionadoId === null) return;

  const reg = registros.find(r => String(r.id) === String(selecionadoId));
  const msg = reg
    ? `Você realmente deseja excluir o registro de ${reg.principalContato} (cca ${reg.cca})?`
    : 'Você realmente deseja excluir este registro?';

  document.getElementById('mensagemExcluir').textContent = msg;
  document.getElementById('modalExcluir').style.display = 'flex';
}

function fecharModalExcluir() {
  document.getElementById('modalExcluir').style.display = 'none';
}

async function excluir() {
  if (selecionadoId === null) return;
  fecharModalExcluir();
  setStatus(statusEdicao, 'Excluindo...');

  try {
    const resp = await fetch(API.remove(selecionadoId), { method: 'DELETE' });
    if (!resp.ok) throw new Error('Erro ao excluir. Verifique o endpoint em API.remove.');

    registros = registros.filter(r => String(r.id) !== String(selecionadoId));
    limparFormulario();
    aplicarFiltro();
    setStatus(statusLista, 'Registro excluído com sucesso.', 'ok');
  } catch (err) {
    console.error(err);
    setStatus(statusEdicao, err.message || 'Erro ao excluir.', 'err');
  }
}

function cancelarEdicao() {
  if (selecionadoId === null) {
    limparFormulario();
    return;
  }
  selecionarLinha(selecionadoId);
  setStatus(statusEdicao, 'Edição cancelada.');
}

function voltar() {
  window.history.back();
}

document.addEventListener('DOMContentLoaded', () => {
  ccaInput.addEventListener('input', e => aplicarMascaraCca(e.target));
  numeroInput.addEventListener('input', e => aplicarMascaraTelefone(e.target));
  filtro.addEventListener('input', aplicarFiltro);

  document.getElementById('modalExcluir').addEventListener('click', ev => {
    if (ev.target.id === 'modalExcluir') fecharModalExcluir();
  });

  limparFormulario();
  carregarLista();
});

window.recarregar = recarregar;
window.salvar = salvar;
window.voltar = voltar;
window.abrirModalExcluir = abrirModalExcluir;
window.fecharModalExcluir = fecharModalExcluir;
window.excluir = excluir;
window.cancelarEdicao = cancelarEdicao;
