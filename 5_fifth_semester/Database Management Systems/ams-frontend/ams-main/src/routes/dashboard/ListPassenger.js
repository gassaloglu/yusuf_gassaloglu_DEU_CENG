import { axios } from '../../index';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { Box, Chip } from '@mui/material';
import { useLoaderData } from 'react-router-dom';
import { EmptyPage } from '../../components/Page';
import { indexToAlphaIndex } from '../../components/Seat';
import { red, green, blue } from '@mui/material/colors';
import dayjs from 'dayjs';

const styles = {
  Essentials: { backgroundColor: blue[500], color: 'white' },
  Advantage: { backgroundColor: green[500], color: 'white' },
  Comfort: { backgroundColor: red[500], color: 'white' },
}

const Fare = ({ type }) => (
  <Chip label={type} sx={styles[type]} />
);

const Question = ({ value }) => (
  <Chip
    size='small'
    label={value ? "Yes" : "No"}
    sx={{
      backgroundColor: value ? 'primary.main' : '',
      color: value ? 'white' : '',
      width: '40px',
    }}
  />
);

const columns = [
  {
    field: 'pnr_no',
    headerName: 'PNR',
    flex: 1.4,
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
    field: 'national_id',
    headerName: 'National ID',
    flex: 1.6,
  },
  {
    field: 'flight_number',
    headerName: 'Flight',
    flex: 1,
  },
  {
    field: 'seat',
    headerName: 'Seat',
    flex: 1,
    valueGetter: params => indexToAlphaIndex(params.value),
  },
  {
    field: 'fare_type',
    headerName: 'Fare',
    flex: 1.6,
    renderCell: params => <Fare type={params.value} />,
  },
  {
    field: 'check_in',
    headerName: 'Check-in',
    flex: 1,
    renderCell: params => <Question value={params.value} />,
  },
  {
    field: 'luggage_id',
    headerName: 'Luggage',
    flex: 1.7,
  },
  {
    field: 'baggage_allowance',
    headerName: 'Baggage allowance',
    flex: 1,
    valueFormatter: params => params.value + ' kg.'
  },
  {
    field: 'extra_luggage',
    headerName: 'Extra Luggage',
    flex: 1,
    valueFormatter: params => params.value + ' kg.'
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
    field: 'cip_member',
    headerName: 'CIP',
    flex: 1,
    renderCell: params => <Question value={params.value} />,
  },
  {
    field: 'vip_member',
    headerName: 'VIP',
    flex: 1,
    renderCell: params => <Question value={params.value} />,
  },
  {
    field: 'disabled',
    headerName: 'Disabled',
    flex: 1,
    renderCell: params => <Question value={params.value} />,
  },
  {
    field: 'child',
    headerName: 'Child',
    flex: 1,
    renderCell: params => <Question value={params.value} />,
  },
  {
    field: 'meal',
    headerName: 'Meal',
    flex: 1,
    renderCell: params => <Question value={params.value} />,
  },
];

export function ListPassenger() {
  const rows = useLoaderData()
    .map(row => Object.assign(row, { id: row.pnr_no }));

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

export async function listPassengerLoader() {
  const response = await axios.get('/passenger/all');
  return response.data;
}