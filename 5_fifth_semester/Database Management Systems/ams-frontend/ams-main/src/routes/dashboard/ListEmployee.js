import { axios } from '../../index';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { Box, Chip } from '@mui/material';
import { useLoaderData } from 'react-router-dom';
import { EmptyPage } from '../../components/Page';
import dayjs from 'dayjs';
import { orange, teal, deepPurple, pink } from '@mui/material/colors';

const permissions = {
  flight_planner: {
    label: 'Flight Planner',
    style: {
      backgroundColor: 'primary.main',
      color: 'white'
    }
  },
  passenger_services: {
    label: 'Passenger Services',
    style: {
      backgroundColor: pink[500],
      color: 'white',
    }
  },
  ground_services: {
    label: 'Ground Services',
    style: {
      backgroundColor: deepPurple[500],
      color: 'white',
    }
  },
  seller: {
    label: 'Seller',
    style: {
      backgroundColor: teal[500],
      color: 'white',
    }
  },
  admin: {
    label: 'Admin',
    style: {
      backgroundColor: orange[500],
      color: 'white',
    }
  },
}

const Permission = ({ value }) => {
  const { label, style } = permissions[value];
  return <Chip label={label} sx={style} />
};

const columns = [
  {
    field: 'national_id',
    headerName: 'National ID',
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
    field: 'title',
    headerName: 'Title',
    flex: 1,
  },
  {
    field: 'permission',
    headerName: 'Permission',
    flex: 1,
    renderCell: params => <Permission value={params.value} />
  },
];

export function ListEmployee() {
  const rows = useLoaderData()
    .map(employee => Object.assign(employee, { id: employee.national_id }));

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

export async function listEmployeeLoader() {
  const response = await axios.get('/profile/employee');
  return response.data;
}