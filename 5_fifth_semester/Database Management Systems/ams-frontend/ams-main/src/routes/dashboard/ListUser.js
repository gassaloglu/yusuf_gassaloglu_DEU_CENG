import { axios } from '../../index';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { Box } from '@mui/material';
import { useLoaderData } from 'react-router-dom';
import { EmptyPage } from '../../components/Page';
import dayjs from 'dayjs';

const columns = [
  {
    field: 'id',
    headerName: 'ID',
    flex: 1,
  },
  {
    field: 'name',
    headerName: 'Name',
    flex: 1,
  },
  {
    field: 'surname',
    headerName: 'Surname',
    flex: 1,
  },
  {
    field: 'gender',
    headerName: 'Gender',
    flex: 1,
  },
  {
    field: 'birth_date',
    headerName: 'Birth',
    flex: 1,
    valueFormatter: params => dayjs(params.value).format('DD/MM/YYYY'),
  },
  {
    field: 'email',
    headerName: 'Email',
    flex: 1,
  },
  {
    field: 'phone',
    headerName: 'Phone',
    flex: 1,
  },
  {
    field: 'money',
    headerName: 'Balance',
    flex: 1,
    valueFormatter: params => params.value + ' ₺'
  },
];

export function ListUser() {
  // Note: MUI DataGrid requires all rows to have a unique `id` property.
  // Since our database handles that case, we don't need to map any rows.
  const rows = useLoaderData()

  return (
    <EmptyPage>
      <Box flexGrow={1} height='100vh'>
        <DataGrid
          rows={rows}
          columns={columns}
          disableRowSelectionOnClick
          slots={{
            toolbar: GridToolbar,
          }}
        />
      </Box>
    </EmptyPage>
  );
}

export async function listUserLoader() {
  const response = await axios.get('/profile/users');
  return response.data;
}