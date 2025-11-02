INSERT INTO public.db_groups (vc_name) VALUES ('default');
INSERT INTO public.db_groups (vc_name) VALUES ('admin');

CREATE INDEX idx_db_groups__vc_name ON db_groups (vc_name);