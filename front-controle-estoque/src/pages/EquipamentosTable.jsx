import React, { useState, useEffect } from 'react';
import {
    Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper,
    Button, IconButton, CircularProgress,
    Typography, Box, Alert, Snackbar
} from '@mui/material';
import {
    AddCircleOutline as AddCircleOutlineIcon,
    Edit as EditIcon,
    Delete as DeleteIcon
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { Pagination } from '@mui/material';
import QuantityControls from '../components/QuantityControls..jsx';
import EquipamentoAddModal from '../components/EquipamentoAddModal.jsx';
import EquipamentoEditModal from '../components/EquipamentoEditModal.jsx';
import {
    getEquipamentos,
    updateEquipamento,
    deleteEquipamento,
    addEquipamento,
    downloadEquipamentos,
    buscarEquipamentosPorNome,
    enviarNotificacaoEmail
} from '../api/equipamentos.js';

export default function EquipamentosTable() {
    const [equipamentos, setEquipamentos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [selected, setSelected] = useState(null);
    const [openAdd, setOpenAdd] = useState(false);
    const [snack, setSnack] = useState({ open: false, message: '', severity: 'success' });
    const navigate = useNavigate();
    const [page, setPage] = useState(0);
    const [size] = useState(10);
    const [totalPages, setTotalPages] = useState(0);
    const [searchTerm, setSearchTerm] = useState('');

    const showSnack = (message, severity = 'success') =>
        setSnack({ open: true, message, severity });

    const handleCloseSnack = () => setSnack((s) => ({ ...s, open: false }));

    useEffect(() => {
        const delayDebounce = setTimeout(async () => {
            try {
                setLoading(true);
                const data = searchTerm
                    ? await buscarEquipamentosPorNome(searchTerm, page, size)
                    : await getEquipamentos(page, size);

                setEquipamentos(data.content || []);
                setTotalPages(data.totalPages || 0);
                setError('');
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        }, 500);

        return () => clearTimeout(delayDebounce);
    }, [searchTerm, page, size]);

    const handleAdd = async (data) => {
        try {
            const added = await addEquipamento(data);
            setEquipamentos((prev) => [...prev, added]);
            showSnack('Equipamento adicionado!');
            setOpenAdd(false);
        } catch (err) {
            showSnack(err.message, 'error');
        }
    };

    const handleEdit = (equip) => setSelected(equip);

    const handleSaveEdit = async (edited) => {
        if (edited.qntFuncionando === 0) {
            const confirm = window.confirm(
                `A quantidade funcionando do equipamento "${edited.nomeCadastradoTasy}"(${edited.tipoEquipamento}) chegou a 0. Deseja notificar a equipe por e-mail?`
            );

            if (confirm) {
                try {
                    await enviarNotificacaoEmail({
                        assunto: 'Sem Estoque ',
                        mensagem: `O equipamento "${edited.nomeCadastradoTasy} "(${edited.tipoEquipamento}) está com estoque zerado.`
                    });
                    showSnack('Notificação enviada por e-mail!');
                } catch (err) {
                    showSnack(`Erro ao enviar notificação: ${err.message}`, 'error');
                }
            }
        }

        try {
            const updated = await updateEquipamento(edited.id, edited);
            setEquipamentos((prev) =>
                prev.map((e) => (e.id === updated.id ? updated : e))
            );
            showSnack('Equipamento atualizado!');
            setSelected(null);
        } catch (err) {
            showSnack(err.message, 'error');
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Confirmar exclusão?')) return;
        try {
            await deleteEquipamento(id);
            setEquipamentos((prev) => prev.filter((e) => e.id !== id));
            showSnack('Equipamento deletado!');
        } catch (err) {
            showSnack(err.message, 'error');
        }
    };

    const handleQuantityChange = async (equip, field, delta) => {
        const updated = {
            ...equip,
            [field]: Math.max(0, equip[field] + delta),
        };
        updated.qntEstoque = updated.qntFuncionando + updated.qntInoperante;
        await handleSaveEdit(updated);
    };

    const handleDownload = async () => {
        try {
            const { blob, filename } = await downloadEquipamentos();

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

    if (loading) return (
        <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
            <CircularProgress /><Typography sx={{ ml: 2 }}>Carregando...</Typography>
        </Box>
    );

    return (
        <Box sx={{ p: 3 }}>
            {error && <Alert severity="error">{error}</Alert>}
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3, position: 'sticky', top: 0, backgroundColor: '#fff', zIndex: 1, paddingBottom: 1 }}>
                <Typography variant="h4">Inventário de Equipamentos</Typography>
                <Box sx={{ display: 'flex', gap: 2 }}>
                    <Button variant="contained" color="primary" onClick={() => navigate('/demandas')}>
                        Demandas
                    </Button>
                    <Button variant="contained" color="primary" onClick={() => navigate('/autorizar')}>
                        Autorização de Compra
                    </Button>
                    <Button startIcon={<AddCircleOutlineIcon />} variant="contained" onClick={() => setOpenAdd(true)}>
                        Equipamento
                    </Button>
                    <Button variant="outlined" color="secondary" onClick={() => navigate('/upload')}>
                        Upload Arquivo
                    </Button>
                    <Button variant="outlined" color="success" onClick={handleDownload}>
                        Baixar Banco de Dados
                    </Button>
                    <Button variant="outlined" color="success" onClick={() => navigate('/')}>
                        Voltar
                    </Button>
                </Box>
            </Box>

            {equipamentos.length === 0 ? (
                <Box sx={{ maxHeight: '60vh', overflowY: 'auto' }}>
                    <Typography color="textSecondary">Nenhum equipamento cadastrado.</Typography>
                </Box>
            ) : (
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                {['Imagem', 'ID', 'Nome Tasy', 'Tipo', 'Marca', 'Modelo', 'Localização', 'Estoque', 'Situação', 'Ações'].map(text => (
                                    <TableCell key={text} align={['Estoque', 'Situação', 'Ações'].includes(text) ? 'center' : 'left'}>
                                        {text}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {equipamentos.map((eq) => (
                                <TableRow key={eq.id}>
                                    <TableCell>
                                        {eq.URLImagem ? (
                                            <img
                                                src={eq.URLImagem}
                                                alt={eq.nomeCadastradoTasy}
                                                style={{ width: 60, height: 60, objectFit: 'cover', borderRadius: 4 }}
                                            />
                                        ) : (
                                            <Typography variant="body2" color="textSecondary">Sem imagem</Typography>
                                        )}
                                    </TableCell>
                                    <TableCell>{eq.id}</TableCell>
                                    <TableCell>{eq.nomeCadastradoTasy}</TableCell>
                                    <TableCell>{eq.tipoEquipamento}</TableCell>
                                    <TableCell>{eq.marca}</TableCell>
                                    <TableCell>{eq.modelo}</TableCell>
                                    <TableCell>{eq.localizacao}</TableCell>
                                    <TableCell align="center">{eq.qntEstoque}</TableCell>
                                    <TableCell align="center">
                                        <QuantityControls
                                            equipamento={eq}
                                            onAddFuncionando={() => handleQuantityChange(eq, 'qntFuncionando', +1)}
                                            onRemoveFuncionando={() => handleQuantityChange(eq, 'qntFuncionando', -1)}
                                            onAddInoperante={() => handleQuantityChange(eq, 'qntInoperante', +1)}
                                            onRemoveInoperante={() => handleQuantityChange(eq, 'qntInoperante', -1)}
                                        />
                                    </TableCell>
                                    <TableCell align="center">
                                        <IconButton color="primary" onClick={() => handleEdit(eq)}>
                                            <EditIcon />
                                        </IconButton>
                                        <IconButton color="error" onClick={() => handleDelete(eq.id)}>
                                            <DeleteIcon />
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}

            {equipamentos.length > 0 && (
                <Box sx={{ display: 'flex', justifyContent: 'center', mt: 2 }}>
                    <Pagination
                        count={totalPages}
                        page={page + 1}
                        onChange={(e, value) => setPage(value - 1)}
                        color="primary"
                        showFirstButton
                        showLastButton
                    />
                </Box>
            )}

            <EquipamentoAddModal open={openAdd} onClose={() => setOpenAdd(false)} onSave={handleAdd} />
            {selected && (
                <EquipamentoEditModal
                    open={!!selected}
                    equipamento={selected}
                    onClose={() => setSelected(null)}
                    onSave={handleSaveEdit}
                />
            )}

            <Snackbar
                open={snack.open}
                autoHideDuration={3000}
                onClose={handleCloseSnack}
                message={snack.message}
                anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
            />
        </Box>
    );
}
