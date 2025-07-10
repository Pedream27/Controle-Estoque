import React, { useState } from 'react';
import {
    Box, Typography, Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper, IconButton, Dialog, DialogTitle,
    DialogContent, DialogActions, Button, TextField, Checkbox, FormControlLabel
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';

const mockData = [
    {
        id: 1,
        prioridade: 'Alta',
        infra: true,
        idHelp: '12345',
        status: 'Em andamento',
        local: 'Sala 203',
        descricao: 'Substituir switch',
        responsavel: 'João - ramal 1234',
        tecnicoUnipel: 'Carlos',
        dataInfra: '2025-07-10',
        dataTerceiro: '2025-07-12',
        agendamentoTerceiro: '14:00',
        tecnicoTerceiro: 'Pedro',
        respostaHelp: 'Aguardando material',
        observacoes: 'Precisa confirmar com compras'
    },
];

export default function DemandasTable() {
    const [demandas, setDemandas] = useState(mockData);
    const [editing, setEditing] = useState(null);

    const handleEditOpen = (demanda) => setEditing({ ...demanda });
    const handleEditClose = () => setEditing(null);

    const handleDelete = (id) => {
        if (window.confirm('Confirmar exclusão?')) {
            setDemandas((prev) => prev.filter((d) => d.id !== id));
        }
    };

    const handleSaveEdit = () => {
        setDemandas((prev) =>
            prev.map((d) => (d.id === editing.id ? editing : d))
        );
        handleEditClose();
    };

    return (
        <Box sx={{ p: 4 }}>
            <Typography variant="h4" gutterBottom>
                Tabela de Demandas
            </Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            {['ID', 'Prioridade', 'Infraestrutura', 'ID Help', 'Status', 'Local', 'Descrição',
                                'Responsável', 'Técnico UniPel', 'Data Infra', 'Data Terceiro',
                                'Agendamento Terceiro', 'Técnico Terceiro', 'Resposta Help', 'Observações', 'Ações'].map((header) => (
                                <TableCell key={header}>{header}</TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {demandas.map((d) => (
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
                                    <IconButton onClick={() => handleEditOpen(d)} color="primary"><EditIcon /></IconButton>
                                    <IconButton onClick={() => handleDelete(d.id)} color="error"><DeleteIcon /></IconButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {/* Modal de Edição */}
            <Dialog open={!!editing} onClose={handleEditClose} fullWidth>
                <DialogTitle>Editar Demanda</DialogTitle>
                <DialogContent>
                    <TextField label="Prioridade" fullWidth margin="dense"
                               value={editing?.prioridade || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, prioridade: e.target.value }))}
                    />
                    <FormControlLabel
                        control={
                            <Checkbox
                                checked={editing?.infra || false}
                                onChange={(e) => setEditing(prev => ({ ...prev, infra: e.target.checked }))}
                            />
                        }
                        label="Mudança de Infraestrutura"
                    />
                    <TextField label="ID Help" fullWidth margin="dense"
                               value={editing?.idHelp || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, idHelp: e.target.value }))}
                    />
                    <TextField label="Status" fullWidth margin="dense"
                               value={editing?.status || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, status: e.target.value }))}
                    />
                    <TextField label="Local" fullWidth margin="dense"
                               value={editing?.local || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, local: e.target.value }))}
                    />
                    <TextField label="Descrição Atividade" fullWidth margin="dense"
                               multiline rows={2}
                               value={editing?.descricao || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, descricao: e.target.value }))}
                    />
                    <TextField label="Responsável / Contato" fullWidth margin="dense"
                               value={editing?.responsavel || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, responsavel: e.target.value }))}
                    />
                    <TextField label="Técnico UniPel" fullWidth margin="dense"
                               value={editing?.tecnicoUnipel || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, tecnicoUnipel: e.target.value }))}
                    />
                    <TextField label="Data chamado Infra" type="date" fullWidth margin="dense"
                               InputLabelProps={{ shrink: true }}
                               value={editing?.dataInfra || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, dataInfra: e.target.value }))}
                    />
                    <TextField label="Data chamado Terceiro" type="date" fullWidth margin="dense"
                               InputLabelProps={{ shrink: true }}
                               value={editing?.dataTerceiro || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, dataTerceiro: e.target.value }))}
                    />
                    <TextField
                        label="Agendamento Terceiro"
                        type="datetime-local"
                        fullWidth
                        margin="dense"
                        InputLabelProps={{ shrink: true }}
                        value={editing?.agendamentoTerceiro || ''}
                        onChange={(e) => setEditing(prev => ({ ...prev, agendamentoTerceiro: e.target.value }))}
                    />
                    <TextField label="Técnico Terceiro" fullWidth margin="dense"
                               value={editing?.tecnicoTerceiro || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, tecnicoTerceiro: e.target.value }))}
                    />
                    <TextField label="Resposta Help" fullWidth margin="dense"
                               value={editing?.respostaHelp || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, respostaHelp: e.target.value }))}
                    />
                    <TextField label="Observações" fullWidth margin="dense"
                               value={editing?.observacoes || ''}
                               onChange={(e) => setEditing(prev => ({ ...prev, observacoes: e.target.value }))}
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
