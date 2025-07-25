import React, { useState, useEffect } from 'react';
import {
  Box, Typography, Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, IconButton, Dialog, DialogTitle,
  DialogContent, DialogActions, Button, TextField, Checkbox,
  FormControlLabel, FormControl, InputLabel, Select, MenuItem
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon, AddCircleOutline } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { getDemandas, addDemanda, downloadDemandas } from '../api/equipamentos.js'; // ajuste conforme seu backend

export default function DemandasTable() {
  const [demandas, setDemandas] = useState([]);
  const [editing, setEditing] = useState(null);
  const navigate = useNavigate();

  const STATUS = ['PENDENTE', 'EM_ANALISE', 'EM_EXECUCAO', 'EM_ANDAMENTO', 'CONCLUIDO'];
  const PRIORIDADES = ['ALTA', 'MEDIA', 'BAIXA'];

  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await getDemandas();
        setDemandas(result);
      } catch (error) {
        console.error('Erro ao carregar demandas:', error);
      }
    };
    fetchData();
  }, []);

  const handleEditOpen = (demanda = {}) => setEditing({ ...demanda });
  const handleEditClose = () => setEditing(null);

  const handleDelete = id => {
    if (window.confirm('Confirmar exclusão?')) {
      setDemandas(prev => prev.filter(d => d.id !== id));
    }
  };

  const handleSaveEdit = async () => {
    if (!editing.id) {
      try {
        const nova = await addDemanda(editing);
        setDemandas(prev => [...prev, nova]);
      } catch (err) {
        console.error('Erro ao adicionar demanda:', err);
      }
    } else {
      setDemandas(prev => prev.map(d => d.id === editing.id ? editing : d));
    }
    handleEditClose();
  };
   const handleDownload = async () => {
          try {
              const { blob, filename } = await downloadDemandas();
  
              const url = window.URL.createObjectURL(blob);
              const a = document.createElement('a');
              a.href = url;
              a.download = filename;
              document.body.appendChild(a);
              a.click();
              a.remove();
              window.URL.revokeObjectURL(url);
          } catch (err) {
              showSnack(`Erro ao baixar banco: ${err.message}`, 'error');
          }
      };

  return (
    <Box sx={{ p: 3 }}>
      {/* Barra de navegação */}
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3 }}>
        <Typography variant="h4">Tabela de Demandas</Typography>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <Button variant="outlined" onClick={() => navigate('/')}>Voltar</Button>
          <Button
            variant="contained"
            color="primary"
            startIcon={<AddCircleOutline />}
            onClick={() => handleEditOpen()}
          >
            Criar Demanda
          </Button>
          <Button variant="contained" color="secondary" onClick={() => navigate('/tabela')}>
            Tabela de Equipamentos
          </Button>
          <Button variant="outlined" color="success" onClick={handleDownload()}>
                                  Baixar Banco de Dados
           </Button>
        </Box>
      </Box>

      {/* Tabela */}
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              {[
                'ID','Prioridade','Infraestrutura','ID Help','Status','Local','Descrição',
                'Responsável','Técnico UniPel','Data Infra','Data Terceiro',
                'Agendamento Terceiro','Técnico Terceiro','Resposta Help','Observações','Ações'
              ].map(header => <TableCell key={header}>{header}</TableCell>)}
            </TableRow>
          </TableHead>
          <TableBody>
            {demandas.map(d => (
              <TableRow key={d.id}>
                <TableCell>{d.id}</TableCell>
                <TableCell>{d.prioridade}</TableCell>
                <TableCell>{d.infra ? 'Sim' : 'Não'}</TableCell>
                <TableCell>{d.idHelp}</TableCell>
                <TableCell>{d.status}</TableCell>
                <TableCell>{d.local}</TableCell>
                <TableCell>{d.descricao}</TableCell>
                <TableCell>{d.responsavel}</TableCell>
                <TableCell>{d.tecnicoUnipel}</TableCell>
                <TableCell>{d.dataInfra}</TableCell>
                <TableCell>{d.dataTerceiro}</TableCell>
                <TableCell>{d.agendamentoTerceiro}</TableCell>
                <TableCell>{d.tecnicoTerceiro}</TableCell>
                <TableCell>{d.respostaHelp}</TableCell>
                <TableCell>{d.observacoes}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleEditOpen(d)} color="primary">
                    <EditIcon />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(d.id)} color="error">
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Modal */}
      <Dialog open={!!editing} onClose={handleEditClose} fullWidth maxWidth="md">
        <DialogTitle>{editing?.id ? 'Editar Demanda' : 'Nova Demanda'}</DialogTitle>
        <DialogContent>
          <FormControl fullWidth margin="dense">
            <InputLabel>Prioridade</InputLabel>
            <Select
              name="prioridade"
              value={editing?.prioridade || ''}
              onChange={e => setEditing(prev => ({ ...prev, prioridade: e.target.value }))}
              label="Prioridade"
            >
              {PRIORIDADES.map(p => <MenuItem key={p} value={p}>{p}</MenuItem>)}
            </Select>
          </FormControl>

          <FormControl fullWidth margin="dense">
            <InputLabel>Status</InputLabel>
            <Select
              name="status"
              value={editing?.status || ''}
              onChange={e => setEditing(prev => ({ ...prev, status: e.target.value }))}
              label="Status"
            >
              {STATUS.map(s => <MenuItem key={s} value={s}>{s}</MenuItem>)}
            </Select>
          </FormControl>

          {[['idHelp', 'ID Help'], ['local', 'Local'], ['descricao', 'Descrição'],
            ['responsavel', 'Responsável / Contato'], ['tecnicoUnipel', 'Técnico UniPel'],
            ['dataInfra', 'Data Infra', 'date'], ['dataTerceiro', 'Data Terceiro', 'date'],
            ['agendamentoTerceiro', 'Agendamento Terceiro', 'datetime-local'],
            ['tecnicoTerceiro', 'Técnico Terceiro'], ['respostaHelp', 'Resposta Help'],
            ['observacoes', 'Observações']
          ].map(([key, label, type = 'text']) => (
            <TextField
              key={key}
              fullWidth
              margin="dense"
              label={label}
              type={type}
              InputLabelProps={['date', 'datetime-local'].includes(type) ? { shrink: true } : undefined}
              multiline={['Descrição', 'Observações'].includes(label)}
              rows={['Descrição', 'Observações'].includes(label) ? 2 : 1}
              value={editing?.[key] || ''}
              onChange={e => setEditing(prev => ({ ...prev, [key]: e.target.value }))}
            />
          ))}

          <FormControlLabel
            control={
              <Checkbox
                checked={editing?.infra || false}
                onChange={e => setEditing(prev => ({ ...prev, infra: e.target.checked }))}
              />
            }
            label="Mudança de Infraestrutura"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleEditClose}>Cancelar</Button>
          <Button onClick={handleSaveEdit} variant="contained" color="primary">Salvar</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}