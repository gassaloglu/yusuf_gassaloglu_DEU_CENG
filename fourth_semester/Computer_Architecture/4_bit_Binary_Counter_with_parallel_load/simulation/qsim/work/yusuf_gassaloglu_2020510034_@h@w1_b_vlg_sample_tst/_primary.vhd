library verilog;
use verilog.vl_types.all;
entity yusuf_gassaloglu_2020510034_HW1_b_vlg_sample_tst is
    port(
        clock           : in     vl_logic;
        count           : in     vl_logic;
        d               : in     vl_logic_vector(0 to 3);
        load            : in     vl_logic;
        sampler_tx      : out    vl_logic
    );
end yusuf_gassaloglu_2020510034_HW1_b_vlg_sample_tst;
