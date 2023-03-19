library verilog;
use verilog.vl_types.all;
entity yusuf_gassaloglu_2020510034_HW2 is
    port(
        ram_out         : out    vl_logic_vector(3 downto 0);
        w_en            : in     vl_logic;
        r_en_ram        : in     vl_logic;
        clk             : in     vl_logic;
        ram_address     : in     vl_logic_vector(3 downto 0);
        ram_data        : in     vl_logic_vector(3 downto 0);
        rom_out         : out    vl_logic_vector(10 downto 0);
        r_en_rom        : in     vl_logic;
        rom_address     : in     vl_logic_vector(3 downto 0)
    );
end yusuf_gassaloglu_2020510034_HW2;
