// src/pages/UploadPage.jsx
import React, { useState } from 'react';
import {
    Box, Typography, Button, Input, Snackbar, Alert, CircularProgress
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function UploadPage() {
    const [file, setFile] = useState(null);
    const [uploading, setUploading] = useState(false);
    const [snack, setSnack] = useState({ open: false, message: '', severity: 'success' });
    const navigate = useNavigate();

    const handleChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleUpload = async () => {
        if (!file) {
            setSnack({ open: true, message: 'Selecione um arquivo!', severity: 'warning' });
            return;
        }

        const formData = new FormData();
        formData.append('file', file);

        try {
            setUploading(true);
            const res = await fetch('http://localhost:8080/api/equipamentos/planilha', {
                method: 'POST',
                body: formData,
            });

            if (!res.ok) {
                const errText = await res.text();
                throw new Error(errText || 'Erro no upload');
            }

            const result = await res.text();
            setSnack({ open: true, message: result || 'Upload feito com sucesso!', severity: 'success' });
            setFile(null);
        } catch (err) {
            setSnack({ open: true, message: `Erro: ${err.message}`, severity: 'error' });
        } finally {
            setUploading(false);
        }
    };

    return (
        <Box sx={{ p: 4 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
                <Typography variant="h5">Upload de Arquivo Excel</Typography>
                <Button variant="outlined" color="primary" onClick={() => navigate('/')}>
                    Voltar
                </Button>
            </Box>

            <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mb: 2 }}>
                <Input type="file" onChange={handleChange} disabled={uploading} />
                <Button
                    variant="contained"
                    onClick={handleUpload}
                    disabled={!file || uploading}
                >
                    {uploading ? 'Enviando...' : 'Enviar'}
                </Button>
                {uploading && <CircularProgress size={24} />}
            </Box>

            {file && !uploading && (
                <Typography variant="body2">Selecionado: {file.name}</Typography>
            )}

            <Snackbar
                open={snack.open}
                autoHideDuration={4000}
                onClose={() => setSnack({ ...snack, open: false })}
                anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
            >
                <Alert onClose={() => setSnack({ ...snack, open: false })} severity={snack.severity}>
                    {snack.message}
                </Alert>
            </Snackbar>
        </Box>
    );
}
