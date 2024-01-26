import { Box, IconButton, Stack, Typography } from '@mui/material';
import { EventSeat as SeatIcon } from '@mui/icons-material';
import styled from '@emotion/styled';

const color = {
  occupied: 'primary.main',
  vacant: 'grey',
  selected: 'orange',
}

export const SeatDescription = ({ variant, label }) => (
  <Stack spacing={1} direction='row' alignItems='center'>
    <SeatIcon sx={{ fontSize: 60, color: color[variant] }} />
    <Typography fontWeight='bold'> {label} </Typography>
  </Stack>
);

const SeatBase = ({ variant, onClick }) => (
  <Box>
    <IconButton onClick={onClick}>
      <SeatIcon sx={{ fontSize: 60, color: color[variant] }} />
    </IconButton>
  </Box >
);

const OccupiedSeat = () => <SeatBase variant='occupied' />;
const VacantSeat = ({ selected, onClick }) =>
  <SeatBase variant={selected ? 'selected' : 'vacant'} onClick={onClick} />;

const Cell = styled(Typography)({
  width: '76px',
  height: '76px',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  fontSize: 30,
  fontWeight: 'bold',
  color: 'grey',
});

const abc = ['A', 'B', 'C', '', 'D', 'E', 'F', '', 'G', 'H', 'I'];

export function SeatPlan({ plan, isSelected, onSelect }) {
  const seatMapper = (rowId, areaId) =>
    (occupied, seatId) => (
      occupied
        ? <OccupiedSeat key={`seat - ${rowId} - ${areaId} - ${seatId}`} />
        : <VacantSeat
          key={`seat - ${rowId} - ${areaId} - ${seatId}`}
          onClick={() => onSelect({ rowId, areaId, seatId })}
          selected={isSelected({ rowId, areaId, seatId })}
        />
    );

  const areaMapper = (rowId) =>
    (area, areaId) => areaId === 0
      ? area.map(seatMapper(rowId, areaId))
      : [<Cell key={`gap - ${rowId} - ${areaId}`} />, ...area.map(seatMapper(rowId, areaId))]

  const rowMapper = (row, rowId) => (
    <Stack key={`row - ${rowId}`} direction='row' >
      <Cell> {rowId + 1}</Cell>
      {row.map(areaMapper(rowId))}
      <Cell> {rowId + 1}</Cell>
    </Stack >
  );
  return (
    <Stack>
      <Stack direction='row' justifyContent='space-evenly'>
        <Cell />
        {abc.map((a, i) => a ? <Cell key={i}>{a}</Cell> : <Cell key={i} />)}
        <Cell />
      </Stack>

      {plan.map(rowMapper)}
    </Stack>
  );
}

export const seatToIndex = ({ rowId, areaId, seatId }) =>
  9 * rowId + 3 * areaId + seatId;

export const seatToAlphaIndex = ({ rowId, areaId, seatId }) =>
  (rowId + 1).toString() + String.fromCharCode(65 + 3 * areaId + seatId);

export const indexToAlphaIndex = i => {
  const rowId = Math.floor(i / 9);
  const offset = i % 9;
  return (rowId + 1).toString() + String.fromCharCode(65 + offset);
}