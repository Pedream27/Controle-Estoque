import React from 'react';
import './HomePage.css'
import { Box, Button, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/08-UNIMED.png'

export default function HomePage() {
    const navigate = useNavigate();

    return (
        <Box
            sx={{
                height: '100vh',
                width: '100vw',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'flex-start', // 👈 mudar isso
                backgroundColor: '#f5f5f5',
                textAlign: 'center',
                padding: 2,
            }}
        >
            <img src={logo} alt="Logo" style={{ width: 200, marginTop: 24 }} />

            <Box
                sx={{
                    padding: 4,
                    borderRadius: 2,
                    mt: 4, // 👈 margem do topo
                    boxShadow: 3,
                    maxWidth: 600,
                    width: '90%',
                }}
            >
                <Typography variant="h3" gutterBottom>
                    Controle Tático Da Infra Disciplina e Precisão
                </Typography>
                <Typography variant="h6" gutterBottom sx={{ mb: 4 }}>
                    Escolha uma opção abaixo:
                </Typography>
                <Box sx={{ display: 'flex', gap: 4, justifyContent: 'center' }}>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={() => navigate('/tabela')}
                        size="large"
                    >
                        Ver Tabela de Equipamentos
                    </Button>
                    <Button
                        variant="outlined"
                        color="secondary"
                        onClick={() => navigate('/autorizar')}
                        size="large"
                    >
                        Solicitar Autorização de Compra
                    </Button>
                    <Button
                        variant="outlined"
                        color="secondary"
                        onClick={() => navigate('/demandas')}
                        size="large"
                    >
                        Ver Tabela de Demandas
                    </Button>
                </Box>
            </Box>
        </Box>

    );
}