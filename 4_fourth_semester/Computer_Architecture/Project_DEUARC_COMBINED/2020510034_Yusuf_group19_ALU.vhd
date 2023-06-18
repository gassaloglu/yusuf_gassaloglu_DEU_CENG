-- Copyright (C) 1991-2013 Altera Corporation
-- Your use of Altera Corporation's design tools, logic functions 
-- and other software and tools, and its AMPP partner logic 
-- functions, and any output files from any of the foregoing 
-- (including device programming or simulation files), and any 
-- associated documentation or information are expressly subject 
-- to the terms and conditions of the Altera Program License 
-- Subscription Agreement, Altera MegaCore Function License 
-- Agreement, or other applicable license agreement, including, 
-- without limitation, that your use is for the sole purpose of 
-- programming logic devices manufactured by Altera and sold by 
-- Altera or its authorized distributors.  Please refer to the 
-- applicable agreement for further details.

-- PROGRAM		"Quartus II 64-Bit"
-- VERSION		"Version 13.0.1 Build 232 06/12/2013 Service Pack 1 SJ Web Edition"
-- CREATED		"Mon Apr 10 18:22:55 2023"

LIBRARY ieee;
USE ieee.std_logic_1164.all; 

LIBRARY work;

ENTITY \2020510034_Yusuf_group19_ALU\ IS 
	PORT
	(
		Rs :  IN  STD_LOGIC_VECTOR(3 DOWNTO 0);
		S2 :  IN  STD_LOGIC_VECTOR(3 DOWNTO 0);
		X :  IN  STD_LOGIC_VECTOR(3 DOWNTO 0);
		V :  OUT  STD_LOGIC;
		Rd :  OUT  STD_LOGIC_VECTOR(3 DOWNTO 0)
	);
END \2020510034_Yusuf_group19_ALU\;

ARCHITECTURE bdf_type OF \2020510034_Yusuf_group19_ALU\ IS 

COMPONENT output_mux
	PORT(data0x : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		 data1x : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		 data2x : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		 data3x : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		 data4x : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		 data5x : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		 data6x : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		 sel : IN STD_LOGIC_VECTOR(2 DOWNTO 0);
		 result : OUT STD_LOGIC_VECTOR(3 DOWNTO 0)
	);
END COMPONENT;

COMPONENT overflow_mux
	PORT(data6 : IN STD_LOGIC;
		 data5 : IN STD_LOGIC;
		 data4 : IN STD_LOGIC;
		 data3 : IN STD_LOGIC;
		 data2 : IN STD_LOGIC;
		 data1 : IN STD_LOGIC;
		 data0 : IN STD_LOGIC;
		 sel : IN STD_LOGIC_VECTOR(2 DOWNTO 0);
		 result : OUT STD_LOGIC
	);
END COMPONENT;

COMPONENT adder
	PORT(cin : IN STD_LOGIC;
		 dataa : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		 datab : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		 overflow : OUT STD_LOGIC;
		 result : OUT STD_LOGIC_VECTOR(3 DOWNTO 0)
	);
END COMPONENT;

COMPONENT shift_mux
	PORT(data1 : IN STD_LOGIC;
		 data0 : IN STD_LOGIC;
		 sel : IN STD_LOGIC;
		 result : OUT STD_LOGIC
	);
END COMPONENT;

SIGNAL	ADD_OUT :  STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL	ADD_V :  STD_LOGIC;
SIGNAL	AND_OUT :  STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL	G :  STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL	INC_OUT :  STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL	INC_V :  STD_LOGIC;
SIGNAL	NOT_OUT :  STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL	SHIFT_OUT :  STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL	SHIFT_V :  STD_LOGIC;
SIGNAL	Vc :  STD_LOGIC;
SIGNAL	XOR_OUT :  STD_LOGIC_VECTOR(3 DOWNTO 0);
SIGNAL	SYNTHESIZED_WIRE_0 :  STD_LOGIC;


BEGIN 



b2v_inst : output_mux
PORT MAP(data0x => SHIFT_OUT,
		 data1x => SHIFT_OUT,
		 data2x => ADD_OUT,
		 data3x => INC_OUT,
		 data4x => AND_OUT,
		 data5x => NOT_OUT,
		 data6x => XOR_OUT,
		 sel => X(2 DOWNTO 0),
		 result => Rd);


b2v_inst1 : overflow_mux
PORT MAP(data6 => G(0),
		 data5 => G(0),
		 data4 => G(0),
		 data3 => INC_V,
		 data2 => ADD_V,
		 data1 => G(0),
		 data0 => SHIFT_V,
		 sel => X(2 DOWNTO 0),
		 result => V);


b2v_inst12 : adder
PORT MAP(cin => G(0),
		 dataa => Rs,
		 datab => S2,
		 overflow => ADD_V,
		 result => ADD_OUT);




b2v_inst15 : shift_mux
PORT MAP(data1 => G(0),
		 data0 => Rs(2),
		 sel => X(0),
		 result => SHIFT_OUT(3));


b2v_inst16 : shift_mux
PORT MAP(data1 => Rs(2),
		 data0 => Rs(0),
		 sel => X(0),
		 result => SHIFT_OUT(1));


b2v_inst18 : shift_mux
PORT MAP(data1 => Rs(3),
		 data0 => Rs(1),
		 sel => X(0),
		 result => SHIFT_OUT(2));


b2v_inst19 : shift_mux
PORT MAP(data1 => Rs(1),
		 data0 => G(0),
		 sel => X(0),
		 result => SHIFT_OUT(0));


XOR_OUT <= S2 XOR Rs;


NOT_OUT <= NOT(Rs);



b2v_inst8 : adder
PORT MAP(cin => Vc,
		 dataa => Rs,
		 datab => G,
		 overflow => INC_V,
		 result => INC_OUT);


SYNTHESIZED_WIRE_0 <= NOT(X(0));



SHIFT_V <= SYNTHESIZED_WIRE_0 AND Rs(3);


AND_OUT <= S2 AND Rs;


Vc <= '1';
END bdf_type;