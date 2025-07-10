// src/pages/DemandasPage.jsx
import React, { useState } from 'react';
import {
    Box,
    Button,
    TextField,
    MenuItem,
    Typography,
    TableContainer,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    Paper, FormControlLabel, Checkbox,
} from '@mui/material';

export default function DemandasPage() {
    const prioridades = ['Alta', 'Média', 'Baixa'];
    const statuses = ['Aberto', 'Em andamento', 'Fechado' , 'Aguardando Terceiros'];
    const tecnicosUnipel = ['Gabriel' , 'Mateus', 'Pedro']

    const [form, setForm] = useState({
        prioridade: '',
        infra: '',
        idHelp: '',
        status: '',
        local: '',
        descricao: '',
        responsavel: '',
        tecnicoUnipel: '',
        dataInfra: '',
        dataTerceiro: '',
        agendamentoTerceiro: '',
        tecnicoTerceiro: '',
        respostaHelp: '',
        observacoes: ''
    });
    const [demandas, setDemandas] = useState([]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setDemandas(prev => [...prev, form]);
        setForm({
            prioridade: '',
            infra: false,
            idHelp: '',
            status: '',
            local: '',
            descricao: '',
            responsavel: '',
            tecnicoUnipel: '',
            dataInfra: '',
            dataTerceiro: '',
            agendamentoTerceiro: '',
            tecnicoTerceiro: '',
            respostaHelp: '',
            observacoes: ''
        });
    };

    return (
        <Box sx={{ p: 4 }}>
            <Typography variant="h4" gutterBottom>Cadastro de Demanda</Typography>

            <Box component="form" onSubmit={handleSubmit} sx={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: 2, mb: 4 }}>
                <TextField
                    select fullWidth label="Prioridade" name="prioridade" value={form.prioridade} onChange={handleChange} required
                >
                    {prioridades.map(p => <MenuItem key={p} value={p}>{p}</MenuItem>)}
                </TextField>

                <FormControlLabel
                    control={
                        <Checkbox
                            checked={form.infra}
                            onChange={(e) => setForm(prev => ({ ...prev, infra: e.target.checked }))}
                            name="infra"
                            color="primary"
                        />
                    }
                    label="Mudança de Infraestrutura"
                />

                <TextField fullWidth label="ID Help" name="idHelp" value={form.idHelp} onChange={handleChange} required />

                <TextField
                    select fullWidth label="Status" name="status" value={form.status} onChange={handleChange} required
                >
                    {statuses.map(s => <MenuItem key={s} value={s}>{s}</MenuItem>)}
                </TextField>

                <TextField fullWidth label="Local de atendimento" name="local" value={form.local} onChange={handleChange} multiline rows={2} required />

                <TextField fullWidth label="Descrição da Atividade" name="descricao" value={form.descricao} onChange={handleChange} multiline rows={2} required />

                <TextField fullWidth label="Responsável / Contato" name="responsavel" value={form.responsavel} onChange={handleChange} required />

                <TextField
                    select fullWidth label="Técnico Unipel" name="tecnicoUnipel" value={form.tecnicoUnipel} onChange={handleChange} required
                >
                    {tecnicosUnipel.map(s => <MenuItem key={s} value={s}>{s}</MenuItem>)}
                </TextField>

                <TextField fullWidth type="date" label="Data chamado Infra" name="dataInfra" value={form.dataInfra} onChange={handleChange} InputLabelProps={{ shrink: true }} />

                <TextField fullWidth type="date" label="Data chamado Terceiro" name="dataTerceiro" value={form.dataTerceiro} onChange={handleChange} InputLabelProps={{ shrink: true }} />

                <TextField fullWidth type="datetime-local" label="Agendamento Terceiro" name="agendamentoTerceiro" value={form.agendamentoTerceiro} onChange={handleChange} InputLabelProps={{ shrink: true }} />

                <TextField fullWidth label="Técnico Terceiro" name="tecnicoTerceiro" value={form.tecnicoTerceiro} onChange={handleChange} />

                <TextField fullWidth label="Resposta Help" name="respostaHelp" value={form.respostaHelp} onChange={handleChange} multiline rows={2} />

                <TextField fullWidth label="Observações" name="observacoes" value={form.observacoes} onChange={handleChange} multiline rows={2} />

                <Box sx={{ gridColumn: 'span 4', textAlign: 'right' }}>
                    <Button type="submit" variant="contained" color="primary">Salvar Demanda</Button>
                </Box>
            </Box>
        </Box>
    );
}