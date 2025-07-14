// src/components/EquipamentoAddModal.jsx
import React, { useState } from 'react';
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
    MenuItem,   
} from '@mui/material';
// Definindo as localizações disponíveis como constantes
// Isso facilita a manutenção e reutilização do código
const LOCALIZACOES_DISPONIVEIS = [
   'ARMARIO_SUPORTE',
    'DATA_CENTER'
];

const initialNewEquipamentoState = {
    nomeCadastradoTasy: '',
    tipoEquipamento: '',
    marca: '',
    modelo: '',
    localizacao: '', 
    qntEstoque: 0,
    qntFuncionando: 0,
    qntInoperante: 0,
};

function EquipamentoAddModal({ open, onClose, onSave }) {
    const [newEquipamento, setNewEquipamento] = useState(initialNewEquipamentoState);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setNewEquipamento((prev) => ({
            ...prev,
            // Converte para número apenas os campos de quantidade
            [name]: name.startsWith('qnt') ? parseInt(value, 10) || 0 : value,
        }));
    };

    const handleSave = () => {
        // Calcular qntEstoque antes de salvar
        const equipamentoToSave = {
            ...newEquipamento,
            qntEstoque: newEquipamento.qntFuncionando + newEquipamento.qntInoperante,
        };
        onSave(equipamentoToSave);
        setNewEquipamento(initialNewEquipamentoState); // Resetar formulário
    };

    if (!open) return null;

    return (
        <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
            <DialogTitle>Adicionar Novo Equipamento</DialogTitle>
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
                            value={newEquipamento.nomeCadastradoTasy}
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
                            value={newEquipamento.tipoEquipamento}
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
                            value={newEquipamento.marca}
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
                            value={newEquipamento.modelo}
                            onChange={handleChange}
                        />
                    </Grid>
                    {/* NOVO CAMPO DE LOCALIZAÇÃO COMO SELECT */}
                    <Grid item xs={12}>
                        <FormControl fullWidth margin="dense" variant="outlined">
                            <InputLabel id="localizacao-label">Localização</InputLabel>
                            <Select
                                labelId="localizacao-label"
                                id="localizacao"
                                name="localizacao"
                                value={newEquipamento.localizacao}
                                onChange={handleChange}
                                label="Localização" // Importante para o "outlined" style
                            >
                                <MenuItem value="">
                                    <em>Nenhum</em>
                                </MenuItem>
                                {LOCALIZACOES_DISPONIVEIS.map((loc) => (
                                    <MenuItem key={loc} value={loc}>
                                        {loc}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                    {/* FIM DO NOVO CAMPO */}
                    <Grid item xs={12} sm={6}>
                        <TextField
                            margin="dense"
                            name="qntFuncionando"
                            label="Quantidade Funcionando"
                            type="number"
                            fullWidth
                            variant="outlined"
                            value={newEquipamento.qntFuncionando}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            margin="dense"
                            name="qntInoperante"
                            label="Quantidade Inoperante"
                            type="number"
                            fullWidth
                            variant="outlined"
                            value={newEquipamento.qntInoperante}
                            onChange={handleChange}
                        />
                    </Grid>
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancelar</Button>
                <Button onClick={handleSave} variant="contained" color="primary">
                    Adicionar
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default EquipamentoAddModal;