// src/components/EquipamentoEditModal.jsx
import React, { useState, useEffect } from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Button,
    Grid,
    FormControl,
    InputLabel,
    Select,
    MenuItem
} from '@mui/material';

// Enum de localizações
const LOCALIZACOES = {
    ARMARIO_SUPORTE: 'ARMARIO_SUPORTE',
    DATA_CENTER: 'DATA_CENTER'
};

const LOCALIZACOES_DISPONIVEIS = Object.values(LOCALIZACOES);

function EquipamentoEditModal({ open, onClose, equipamento, onSave }) {
    const [editedEquipamento, setEditedEquipamento] = useState({
        nomeCadastradoTasy: '',
        tipoEquipamento: '',
        marca: '',
        modelo: '',
        localizacao: LOCALIZACOES.ARMARIO_SUPORTE,
        qntFuncionando: 0,
        qntInoperante: 0,
        qntEstoque: 0,
    });

    useEffect(() => {
        if (equipamento) {
            setEditedEquipamento({
                ...equipamento,
                qntFuncionando: equipamento.qntFuncionando || 0,
                qntInoperante: equipamento.qntInoperante || 0,
                qntEstoque: (equipamento.qntFuncionando || 0) + (equipamento.qntInoperante || 0),
                localizacao: equipamento.localizacao || LOCALIZACOES.ARMARIO_SUPORTE
            });
        }
    }, [equipamento]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedEquipamento((prev) => ({
            ...prev,
            [name]: (name === 'qntFuncionando' || name === 'qntInoperante') ? parseInt(value, 10) || 0 : value,
        }));
    };

    const handleSave = () => {
        const equipamentoToSave = {
            ...editedEquipamento,
            qntEstoque: (editedEquipamento.qntFuncionando || 0) + (editedEquipamento.qntInoperante || 0),
        };
        onSave(equipamentoToSave);
    };

    if (!open || !equipamento) return null;

    return (
        <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
            <DialogTitle>Editar Equipamento: {equipamento?.nomeCadastradoTasy}</DialogTitle>
            <DialogContent dividers>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            margin="dense"
                            name="nomeCadastradoTasy"
                            label="Nome Cadastrado Tasy"
                            type="text"
                            fullWidth
                            variant="outlined"
                            value={editedEquipamento.nomeCadastradoTasy || ''}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            margin="dense"
                            name="tipoEquipamento"
                            label="Tipo Equipamento"
                            type="text"
                            fullWidth
                            variant="outlined"
                            value={editedEquipamento.tipoEquipamento || ''}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            margin="dense"
                            name="marca"
                            label="Marca"
                            type="text"
                            fullWidth
                            variant="outlined"
                            value={editedEquipamento.marca || ''}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            margin="dense"
                            name="modelo"
                            label="Modelo"
                            type="text"
                            fullWidth
                            variant="outlined"
                            value={editedEquipamento.modelo || ''}
                            onChange={handleChange}
                        />
                    </Grid>

                    <Grid item xs={12}>
                        <FormControl fullWidth margin="dense" variant="outlined">
                            <InputLabel id="localizacao-label">Localização</InputLabel>
                            <Select
                                labelId="localizacao-label"
                                id="localizacao"
                                name="localizacao"
                                value={editedEquipamento.localizacao || ''}
                                onChange={handleChange}
                                label="Localização"
                            >
                                <MenuItem value="">
                                    <em>Selecione...</em>
                                </MenuItem>
                                {LOCALIZACOES_DISPONIVEIS.map((loc) => (
                                    <MenuItem key={loc} value={loc}>
                                        {loc}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>

                    <Grid item xs={12} sm={4}>
                        <TextField
                            margin="dense"
                            name="qntEstoque"
                            label="Quantidade em Estoque"
                            type="number"
                            fullWidth
                            variant="outlined"
                            value={editedEquipamento.qntEstoque || ''}
                            onChange={handleChange}
                            InputProps={{ readOnly: true }}
                        />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <TextField
                            margin="dense"
                            name="qntFuncionando"
                            label="Quantidade Funcionando"
                            type="number"
                            fullWidth
                            variant="outlined"
                            value={editedEquipamento.qntFuncionando || ''}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <TextField
                            margin="dense"
                            name="qntInoperante"
                            label="Quantidade Inoperante"
                            type="number"
                            fullWidth
                            variant="outlined"
                            value={editedEquipamento.qntInoperante || ''}
                            onChange={handleChange}
                        />
                    </Grid>
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancelar</Button>
                <Button onClick={handleSave} variant="contained" color="primary">
                    Salvar
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default EquipamentoEditModal;
