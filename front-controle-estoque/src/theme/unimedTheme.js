// src/theme/unimedTheme.js
import { createTheme } from '@mui/material/styles';

const unimedTheme = createTheme({
    palette: {
        primary: {
            main: '#009245',      // Verde forte Unimed
            contrastText: '#fff'
        },
        secondary: {
            main: '#70bb03',      // Verde claro secundário
            contrastText: '#fff'
        },
    },
    components: {
        MuiButton: {
            styleOverrides: {
                root: {
                    textTransform: 'none',
                    borderRadius: 8,
                },
            },
        },
    },
    typography: {
        fontFamily: 'Roboto, Arial, sans-serif',
    },
});

export default unimedTheme;
