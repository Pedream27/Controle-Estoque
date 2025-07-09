import React from 'react';
import { Box, Typography, TextField, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function AutorizacaoPage() {
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        alert('Autorização enviada!');
        // Aqui você pode fazer um fetch POST com os dados
    };

    return (
        <Box sx={{ p: 4 }}>
            <Typography variant="h4" gutterBottom>Solicitação de Compra</Typography>
            <form onSubmit={handleSubmit}>
                <TextField fullWidth label="Nome do Solicitante" margin="normal" required />
                <TextField fullWidth label="Motivo" margin="normal" required multiline rows={2} />
                <TextField fullWidth label="Produto" margin="normal" required multiline rows={3} />
                <TextField fullWidth label="Valor" margin="normal" required multiline rows={3} />
                <TextField fullWidth label="Email para Contato" type="email" margin="normal" required />
                <Box sx={{ mt: 2, display: 'flex', gap: 2 }}>
                    <Button variant="contained" color="primary" type="submit">
                        Enviar Solicitação
                    </Button>
                    <Button variant="outlined" color="info" onClick={() => navigate('/')}>
                        Voltar à Tabela
                    </Button>
                </Box>
            </form>
        </Box>
    );
}
