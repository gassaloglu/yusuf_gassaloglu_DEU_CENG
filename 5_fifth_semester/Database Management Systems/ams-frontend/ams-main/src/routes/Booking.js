import { createContext, useContext, useMemo, useState } from "react";
import { useLoaderData, useRouteError, Link } from 'react-router-dom';
import { chunk, isEqual } from 'underscore';
import { useImmer } from 'use-immer';
import { axios } from '../index';
import dayjs from "dayjs";

import { Box, Button, Divider, Stack, Step, StepButton, Stepper, Typography, Grid } from "@mui/material";
import { ArrowRight, ErrorOutline, CheckCircleOutline, WarningAmber } from '@mui/icons-material';

import Page from "../components/Page";
import PassengerForm from '../components/PassengerForm';
import { SeatDescription, SeatPlan, seatToAlphaIndex, seatToIndex } from '../components/Seat';
import Error from '../components/Error';
import { Center } from '../components/Styled';
import { FlightDetails, getPrice } from "../components/Flight";
import { LoadingButton } from "@mui/lab";

export const BookingContext = createContext({});

const NUMBER_OF_SEATS = 270;

const steps = [
  <PassengerForm />,
  <SeatSelection />,
  <Payment />
];

export function Booking() {
  const { flight, occupation, plan } = useLoaderData();
  const [step, setStep] = useState(0);
  const [booking, updateBooking] = useImmer({
    flight_number: flight.flight_number,
    ticket_type: plan,
    national_id: '',
    name: '',
    surname: '',
    email: '',
    phone: '',
    gender: 'Male',
    disabled: 0,
    seat: null,
    birth_date: null,
  });

  const nextStep = () => setStep(step + 1)

  const seats = useMemo(() => {
    const seats = Array(NUMBER_OF_SEATS).fill(false);
    occupation.forEach(seat => seats[seat] = true);
    return chunk(chunk(seats, 3), 3);
  }, [occupation]);

  const context = { step, setStep, nextStep, booking, updateBooking, seats, flight };

  return (
    <BookingContext.Provider value={context}>
      <Page>
        <Box padding={2} display='flex' justifyContent='center'>
          <Stack sx={{ width: '900px' }} spacing={3} alignItems='stretch'>
            <FlightDetails
              from={flight.departure_airport}
              to={flight.destination_airport}
              date={flight.departure_time}
            />

            <Steps />

            <Stack alignItems='center'>
              {steps[step]}
            </Stack>
          </Stack >
        </Box >
      </Page>
    </BookingContext.Provider>
  );
}

export function BookingErrorBoundary() {
  const error = useRouteError();

  return (
    <Page>
      <Center>
        {
          error.response
            ? <Error title="Flight not found">A scheduled flight can not be found or your ticket type is invalid.</Error>
            : <Error title="Something went wrong">It appears that a network error has occurred.</Error>
        }
      </Center >
    </Page>
  );
}

export async function bookingLoader({ params: { flight_number, plan } }) {
  const [{ data: [flight] }, { data: occupation }] = await Promise.all([
    axios.get(`/flight/${flight_number}`),
    axios.get(`/flight/seats?id=${flight_number}`),
  ]);

  if (!["Essentials", "Advantage", "Comfort"].includes(plan))
    throw new Error(`Invalid ticket type "${plan}"`);

  return { flight, occupation, plan };
}

function SeatSelection() {
  const { nextStep, updateBooking, seats } = useContext(BookingContext);
  const [selectedSeat, setSelectedSeat] = useState(null);

  const handleSubmit = () => {
    updateBooking(draft => {
      draft.seat = selectedSeat;
    });

    nextStep();
  }

  return (
    <>
      <Stack direction='row' spacing={5}>
        <Stack spacing={2} direction='row' alignItems='center' divider={<Divider orientation="vertical" flexItem />}>
          <SeatDescription variant="occupied" label="Occupied" />
          <SeatDescription variant="vacant" label="Vacant" />
          <SeatDescription variant="selected" label="Selected" />
        </Stack>

        <Button
          sx={{ width: '150px' }}
          disabled={selectedSeat === null}
          variant='contained'
          endIcon={<ArrowRight />}
          onClick={handleSubmit}
        >
          Continue
        </Button>
      </Stack>

      <SeatPlan
        plan={seats}
        isSelected={seat => isEqual(seat, selectedSeat)}
        onSelect={setSelectedSeat}
      />
    </>
  );
}

function Steps() {
  const { step, setStep } = useContext(BookingContext);

  return (
    <Stepper alternativeLabel activeStep={step}>
      <Step>
        <StepButton onClick={() => setStep(0)}>
          Passenger information
        </StepButton>
      </Step>
      <Step>
        <StepButton onClick={() => setStep(1)}>
          Seat selection
        </StepButton>
      </Step>
      <Step>
        <StepButton onClick={() => setStep(2)}>
          Payment
        </StepButton>
      </Step>
    </Stepper>
  )
}

const Detail = ({ label, children }) =>
  <Typography> <strong>{label}</strong>: {children}</Typography>;

const Status = ({ Icon, children }) => (
  <Stack sx={{ minHeight: '200px' }} alignItems='center'>
    <Icon sx={{ fontSize: '100px', color: 'grey.500', mb: 1 }} />
    {children}
  </Stack>
);

function Payment() {
  const { booking, flight } = useContext(BookingContext);

  const [pnr, setPnr] = useState(null);
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState(null);
  const [failMessage, setFailMessage] = useState('');

  const handleClick = () => {
    const payload = {
      ...booking,
      birth_date: dayjs(booking.birth_date).format("YYYY-MM-DD"),
      seat: seatToIndex(booking.seat).toString(),
      child: Number(dayjs().diff(booking.birth_date, 'year', true) < 10).toString(),
      disabled: booking.disabled.toString(),
    }

    setLoading(true);

    axios.post('/ticket/payment', payload)
      .then(({ data: { pnr } }) => {
        setPnr(pnr);
        setStatus('success');
      })
      .catch(error => {
        if (error.response) {
          setStatus('fail');
          setFailMessage(error.response.data);
        } else {
          setStatus('error');
        }
      })
      .finally(() => setLoading(false));
  }

  if (status) {
    return (
      <Box>
        {
          status === "fail" &&
          <Status Icon={ErrorOutline}>
            <Typography>
              Payment failed.
            </Typography>
            <Typography variant='caption'>
              Status: {failMessage}
            </Typography>
          </Status>
        }

        {
          status === "error" &&
          <Status Icon={WarningAmber}>
            <Typography>
              Something went wrong.
            </Typography>
          </Status>
        }

        {
          status === "success" &&
          <Status Icon={CheckCircleOutline}>
            <Typography>
              Your reservation number is: <strong> {pnr} </strong>
            </Typography>

            <Button
              sx={{ mt: 1 }}
              component={Link}
              to={`/checkin/${pnr}/${booking.surname}`}
              variant="contained">
              See check-in details
            </Button>
          </Status>
        }
      </Box>
    );
  } else {
    return (
      <Stack alignSelf='stretch' spacing={4} alignItems='center'>
        <Typography fontWeight='bold' variant='h5'>
          Overview of your booking details
        </Typography>

        <Stack alignSelf='stretch' direction='row' justifyContent='space-evenly' >
          <Box>
            <Typography variant='h6'> Passenger </Typography>
            <Divider sx={{ my: .5 }} />
            <Detail label="Fullname">{booking.name + " " + booking.surname}</Detail>
            <Detail label="National ID">{booking.national_id}</Detail>
            <Detail label="Phone">{booking.phone}</Detail>
            <Detail label="Email">{booking.email}</Detail>
            <Detail label="Disabled">{booking.disabled ? "Yes" : "No"}</Detail>
          </Box>
          <Box>
            <Typography variant='h6'> Flight </Typography>
            <Divider sx={{ my: .5 }} />
            <Detail label="From">{flight.departure_airport}</Detail>
            <Detail label="To">{flight.destination_airport}</Detail>
            <Detail label="Date">{dayjs(flight.departure_time).format("L LT")}</Detail>
            <Detail label="Ticket type">{booking.ticket_type}</Detail>
            <Detail label="Seat">{seatToAlphaIndex(booking.seat)}</Detail>
          </Box>
        </Stack>

        <Typography variant='h6'>
          Total price: {getPrice(flight.price, booking.ticket_type)} ₺
        </Typography>

        <LoadingButton loading={loading} variant="contained" onClick={handleClick}>
          Purchase
        </LoadingButton>
      </Stack>
    );
  }
}
