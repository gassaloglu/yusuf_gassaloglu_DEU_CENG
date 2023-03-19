library verilog;
use verilog.vl_types.all;
entity yusuf_gassaloglu_2020510034_HW2_vlg_sample_tst is
    port(
        clk             : in     vl_logic;
        r_en_ram        : in     vl_logic;
        r_en_rom        : in     vl_logic;
        ram_address     : in     vl_logic_vector(3 downto 0);
        ram_data        : in     vl_logic_vector(3 downto 0);
        rom_address     : in     vl_logic_vector(3 downto 0);
        w_en            : in     vl_logic;
        sampler_tx      : out    vl_logic
    );
end yusuf_gassaloglu_2020510034_HW2_vlg_sample_tst;
