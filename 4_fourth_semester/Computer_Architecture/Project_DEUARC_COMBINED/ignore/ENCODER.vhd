-- megafunction wizard: %8B10B Encoder-Decoder v13.0%
-- GENERATION: XML

-- ============================================================
-- Megafunction Name(s):
-- 			ENCODER_enc8b10b
-- ============================================================
-- Generated by 8B10B Encoder-Decoder 13.0 [Altera, IP Toolbench 1.3.0 Build 232]
-- ************************************************************
-- THIS IS A WIZARD-GENERATED FILE. DO NOT EDIT THIS FILE!
-- ************************************************************
-- Copyright (C) 1991-2023 Altera Corporation
-- Any megafunction design, and related net list (encrypted or decrypted),
-- support information, device programming or simulation file, and any other
-- associated documentation or information provided by Altera or a partner
-- under Altera's Megafunction Partnership Program may be used only to
-- program PLD devices (but not masked PLD devices) from Altera.  Any other
-- use of such megafunction design, net list, support information, device
-- programming or simulation file, or any other related documentation or
-- information is prohibited for any other purpose, including, but not
-- limited to modification, reverse engineering, de-compiling, or use with
-- any other silicon devices, unless such use is explicitly licensed under
-- a separate agreement with Altera or a megafunction partner.  Title to
-- the intellectual property, including patents, copyrights, trademarks,
-- trade secrets, or maskworks, embodied in any such megafunction design,
-- net list, support information, device programming or simulation file, or
-- any other related documentation or information provided by Altera or a
-- megafunction partner, remains with Altera, the megafunction partner, or
-- their respective licensors.  No other licenses, including any licenses
-- needed under any third party's intellectual property, are provided herein.

library IEEE;
use IEEE.std_logic_1164.all;

ENTITY ENCODER IS
	PORT (
		clk	: IN STD_LOGIC;
		reset_n	: IN STD_LOGIC;
		idle_ins	: IN STD_LOGIC;
		kin	: IN STD_LOGIC;
		ena	: IN STD_LOGIC;
		datain	: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
		rdin	: IN STD_LOGIC;
		rdforce	: IN STD_LOGIC;
		kerr	: OUT STD_LOGIC;
		dataout	: OUT STD_LOGIC_VECTOR (9 DOWNTO 0);
		valid	: OUT STD_LOGIC;
		rdout	: OUT STD_LOGIC;
		rdcascade	: OUT STD_LOGIC
	);
END ENCODER;

ARCHITECTURE SYN OF ENCODER IS


	COMPONENT ENCODER_enc8b10b
	PORT (
		clk	: IN STD_LOGIC;
		reset_n	: IN STD_LOGIC;
		idle_ins	: IN STD_LOGIC;
		kin	: IN STD_LOGIC;
		ena	: IN STD_LOGIC;
		datain	: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
		rdin	: IN STD_LOGIC;
		rdforce	: IN STD_LOGIC;
		kerr	: OUT STD_LOGIC;
		dataout	: OUT STD_LOGIC_VECTOR (9 DOWNTO 0);
		valid	: OUT STD_LOGIC;
		rdout	: OUT STD_LOGIC;
		rdcascade	: OUT STD_LOGIC
	);

	END COMPONENT;

BEGIN

	ENCODER_enc8b10b_inst : ENCODER_enc8b10b
	PORT MAP (
		clk  =>  clk,
		reset_n  =>  reset_n,
		idle_ins  =>  idle_ins,
		kin  =>  kin,
		ena  =>  ena,
		datain  =>  datain,
		kerr  =>  kerr,
		dataout  =>  dataout,
		valid  =>  valid,
		rdin  =>  rdin,
		rdforce  =>  rdforce,
		rdout  =>  rdout,
		rdcascade  =>  rdcascade
	);


END SYN;


-- =========================================================
-- 8B10B Encoder-Decoder Wizard Data
-- ===============================
-- DO NOT EDIT FOLLOWING DATA
-- @Altera, IP Toolbench@
-- Warning: If you modify this section, 8B10B Encoder-Decoder Wizard may not be able to reproduce your chosen configuration.
-- 
-- Retrieval info: <?xml version="1.0"?>
-- Retrieval info: <MEGACORE title="8B10B Encoder-Decoder MegaCore Function"  version="13.0"  build="232"  iptb_version="1.3.0 Build 232"  format_version="120" >
-- Retrieval info:  <NETLIST_SECTION class="altera.ipbu.flowbase.netlist.model.MVCModel"  active_core="ENCODER_enc8b10b" >
-- Retrieval info:   <STATIC_SECTION>
-- Retrieval info:    <PRIVATES>
-- Retrieval info:     <NAMESPACE name = "parameterization">
-- Retrieval info:      <PRIVATE name = "p_ed8b10b" value="1"  type="BOOLEAN"  enable="1" />
-- Retrieval info:      <PRIVATE name = "megawizard2" value="1"  type="STRING"  enable="1" />
-- Retrieval info:      <PRIVATE name = "activate_atstartup" value="1"  type="STRING"  enable="1" />
-- Retrieval info:      <PRIVATE name = "window_location" value="center"  type="STRING"  enable="1" />
-- Retrieval info:      <PRIVATE name = "p_iptb_top" value="ENCODER"  type="STRING"  enable="1" />
-- Retrieval info:      <PRIVATE name = "p_direction" value="encoder"  type="STRING"  enable="1" />
-- Retrieval info:      <PRIVATE name = "p_port_type" value="flop"  type="STRING"  enable="1" />
-- Retrieval info:      <PRIVATE name = "p_family_id" value="df_cycloneIII"  type="STRING"  enable="1" />
-- Retrieval info:      <PRIVATE name = "p_cbx_hdl_language" value="vhdl"  type="STRING"  enable="1" />
-- Retrieval info:     </NAMESPACE>
-- Retrieval info:     <NAMESPACE name = "quartus_settings">
-- Retrieval info:      <PRIVATE name = "WEB_BROWSER" value="netscape"  type="STRING"  enable="1" />
-- Retrieval info:     </NAMESPACE>
-- Retrieval info:     <NAMESPACE name = "simgen_enable">
-- Retrieval info:      <PRIVATE name = "language" value="VHDL"  type="STRING"  enable="1" />
-- Retrieval info:      <PRIVATE name = "enabled" value="0"  type="BOOLEAN"  enable="1" />
-- Retrieval info:     </NAMESPACE>
-- Retrieval info:     <NAMESPACE name = "hdl_generator">
-- Retrieval info:      <PRIVATE name = "report" value="1"  type="BOOLEAN"  enable="1" />
-- Retrieval info:      <PRIVATE name = "blackbox" value="0"  type="BOOLEAN"  enable="1" />
-- Retrieval info:      <PRIVATE name = "component" value="0"  type="BOOLEAN"  enable="1" />
-- Retrieval info:      <PRIVATE name = "symbol" value="0"  type="BOOLEAN"  enable="1" />
-- Retrieval info:     </NAMESPACE>
-- Retrieval info:     <NAMESPACE name = "simgen">
-- Retrieval info:      <PRIVATE name = "filename" value="ENCODER.vho"  type="STRING"  enable="1" />
-- Retrieval info:     </NAMESPACE>
-- Retrieval info:     <NAMESPACE name = "greybox">
-- Retrieval info:      <PRIVATE name = "filename" value="ENCODER_syn.v"  type="STRING"  enable="1" />
-- Retrieval info:     </NAMESPACE>
-- Retrieval info:     <NAMESPACE name = "serializer"/>
-- Retrieval info:    </PRIVATES>
-- Retrieval info:    <FILES/>
-- Retrieval info:    <PORTS/>
-- Retrieval info:    <LIBRARIES/>
-- Retrieval info:   </STATIC_SECTION>
-- Retrieval info:  </NETLIST_SECTION>
-- Retrieval info: </MEGACORE>
-- =========================================================