library verilog;
use verilog.vl_types.all;
entity yusuf_gassaloglu_2020510034_HW1_b is
    port(
        carry           : out    vl_logic;
        load            : in     vl_logic;
        clock           : in     vl_logic;
        count           : in     vl_logic;
        d               : in     vl_logic_vector(0 to 3);
        q               : out    vl_logic_vector(3 downto 0)
    );
end yusuf_gassaloglu_2020510034_HW1_b;
