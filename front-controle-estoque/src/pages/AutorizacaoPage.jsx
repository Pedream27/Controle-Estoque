import React, { useState } from 'react';
import { Box, Typography, TextField, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function AutorizacaoPage() {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [produto, setProduto] = useState('');
    const [valorEstimado, setValorEstimado] = useState('');
    const [qntDesejada, setQntDesejada] = useState('');
    const [motivo, setMotivo] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const payload = {
            nomeSolicitante: nome,
            emailSolicitante: email,
            nomeEquipamento: produto,
            quantidadeDesejada: qntDesejada,
            motivo: motivo,
            valorEstimado: valorEstimado
        };

        try {
            const res = await fetch('http://localhost:8080/api/email/compras', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload),
            });

            if (!res.ok) throw new Error('Erro ao enviar solicitação');

            alert('Solicitação enviada com sucesso!');
            navigate('/');
        } catch (err) {
            alert(err.message);
        }
    };

    return (
        <Box sx={{ p: 4 }}>
            <Typography variant="h4" gutterBottom>Solicitação de Compra</Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    fullWidth
                    label="Nome do Solicitante"
                    margin="normal"
                    required
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                />
                <TextField
                    fullWidth
                    label="Email para Contato"
                    type="email"
                    margin="normal"
                    required
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <TextField
                    fullWidth
                    label="Produto"
                    margin="normal"
                    required
                    multiline
                    rows={3}
                    value={produto}
                    onChange={(e) => setProduto(e.target.value)}
                />
                <TextField
                    fullWidth
                    label="Quantidade Desejada"
                    margin="normal"
                    required
                    multiline
                    rows={3}
                    value={produto}
                    onChange={(e) => setQntDesejada(e.target.value)}
                />
                <TextField
                    fullWidth
                    label="Valor Estimado"
                    margin="normal"
                    required
                    multiline
                    rows={3}
                    value={produto}
                    onChange={(e) => setValorEstimado(e.target.value)}
                />
                <TextField
                    fullWidth
                    label="Motivo"
                    margin="normal"
                    required
                    multiline
                    rows={3}
                    value={produto}
                    onChange={(e) => setMotivo(e.target.value)}
                />
                <Box sx={{ mt: 2, display: 'flex', gap: 2 }}>
                    <Button variant="contained" color="primary" type="submit">
                        Enviar Solicitação
                    </Button>
                    <Button variant="outlined" color="primary" onClick={() => navigate('/')}>
                        Voltar
                    </Button>
                </Box>
            </form>
        </Box>
    );
}
