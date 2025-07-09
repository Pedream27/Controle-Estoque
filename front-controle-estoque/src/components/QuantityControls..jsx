import React from 'react';
import { Box, Typography, IconButton } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';

const QuantityLine = ({ label, value, onAdd, onRemove }) => (
    <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
        <Typography variant="body2" sx={{ minWidth: 80 }}>{label}:</Typography>
        <Typography variant="body2" sx={{ minWidth: 20, textAlign: 'center' }}>{value}</Typography>
        <IconButton size="small" color="success" onClick={onAdd}><AddIcon fontSize="small" /></IconButton>
        <IconButton size="small" color="error" onClick={onRemove}><RemoveIcon fontSize="small" /></IconButton>
    </Box>
);

export default function QuantityControls({
                                             equipamento,
                                             onAddFuncionando,
                                             onRemoveFuncionando,
                                             onAddInoperante,
                                             onRemoveInoperante,
                                         }) {
    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 0.5 }}>
            <QuantityLine label="Funcionando"
                          value={equipamento.qntFuncionando}
                          onAdd={() => onAddFuncionando(equipamento.id)}
                          onRemove={() => onRemoveFuncionando(equipamento.id)}
            />
            <QuantityLine label="Inoperante"
                          value={equipamento.qntInoperante}
                          onAdd={() => onAddInoperante(equipamento.id)}
                          onRemove={() => onRemoveInoperante(equipamento.id)}
            />
        </Box>
    );
}
